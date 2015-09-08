package ch.cyberduck.core.s3;

/*
 * Copyright (c) 2002-2013 David Kocher. All rights reserved.
 * http://cyberduck.ch/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Bug fixes, suggestions and comments should be sent to feedback@cyberduck.ch
 */

import ch.cyberduck.core.Credentials;
import ch.cyberduck.core.LoginCallback;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.PathContainerService;
import ch.cyberduck.core.collections.Partition;
import ch.cyberduck.core.exception.AccessDeniedException;
import ch.cyberduck.core.exception.BackgroundException;
import ch.cyberduck.core.features.Delete;
import ch.cyberduck.core.features.Versioning;
import ch.cyberduck.core.preferences.PreferencesFactory;

import org.apache.log4j.Logger;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.MultipartUpload;
import org.jets3t.service.model.MultipleDeleteResult;
import org.jets3t.service.model.container.ObjectKeyAndVersion;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version $Id$
 */
public class S3MultipleDeleteFeature implements Delete {
    private static final Logger log = Logger.getLogger(S3MultipleDeleteFeature.class);

    private S3Session session;

    private PathContainerService containerService
            = new S3PathContainerService();

    private S3MultipartService multipartService;

    private Versioning versioningService;

    public S3MultipleDeleteFeature(final S3Session session) {
        this.session = session;
        this.versioningService = session.getFeature(Versioning.class);
        this.multipartService = new S3MultipartService(session);
    }

    public void delete(final List<Path> files, final LoginCallback prompt, final Callback callback) throws BackgroundException {
        if(files.size() == 1) {
            new S3DefaultDeleteFeature(session).delete(files, prompt, callback);
        }
        else {
            final Map<Path, List<ObjectKeyAndVersion>> map = new HashMap<Path, List<ObjectKeyAndVersion>>();
            for(Path file : files) {
                if(containerService.isContainer(file)) {
                    continue;
                }
                callback.delete(file);
                final Path container = containerService.getContainer(file);
                final List<ObjectKeyAndVersion> keys = new ArrayList<ObjectKeyAndVersion>();
                // Always returning 204 even if the key does not exist. Does not return 404 for non-existing keys
                keys.add(new ObjectKeyAndVersion(containerService.getKey(file), file.attributes().getVersionId()));
                if(map.containsKey(container)) {
                    map.get(container).addAll(keys);
                }
                else {
                    map.put(container, keys);
                }
            }
            // Iterate over all containers and delete list of keys
            for(Map.Entry<Path, List<ObjectKeyAndVersion>> entry : map.entrySet()) {
                final Path container = entry.getKey();
                final List<ObjectKeyAndVersion> keys = entry.getValue();
                this.delete(container, keys, prompt);
            }
            for(Path file : files) {
                if(containerService.isContainer(file)) {
                    callback.delete(file);
                    // Finally delete bucket itself
                    try {
                        session.getClient().deleteBucket(containerService.getContainer(file).getName());
                    }
                    catch(ServiceException e) {
                        throw new ServiceExceptionMappingService().map("Cannot delete {0}", e, file);
                    }
                }
            }
        }
        for(Path file : files) {
            if(file.isFile()) {
                try {
                    // Delete interrupted multipart uploads
                    for(MultipartUpload upload : multipartService.find(file)) {
                        multipartService.delete(upload);
                    }
                }
                catch(AccessDeniedException e) {
                    // Workaround for #9000
                    log.warn(String.format("Failure looking for multipart uploads. %s", e.getMessage()));
                }
            }
        }
    }

    /**
     * @param container Bucket
     * @param keys      Key and version ID for versioned object or null
     * @throws ch.cyberduck.core.exception.ConnectionCanceledException Authentication canceled for MFA delete
     */
    protected void delete(final Path container, final List<ObjectKeyAndVersion> keys, final LoginCallback prompt)
            throws BackgroundException {
        try {
            if(versioningService != null
                    && versioningService.getConfiguration(container).isMultifactor()) {
                final Credentials factor = versioningService.getToken(prompt);
                final MultipleDeleteResult result = session.getClient().deleteMultipleObjectsWithMFA(container.getName(),
                        keys.toArray(new ObjectKeyAndVersion[keys.size()]),
                        factor.getUsername(),
                        factor.getPassword(),
                        // Only include errors in response
                        true);
                if(result.hasErrors()) {
                    for(MultipleDeleteResult.ErrorResult error : result.getErrorResults()) {
                        final ServiceException failure = new ServiceException();
                        failure.setErrorCode(error.getErrorCode());
                        failure.setErrorMessage(error.getMessage());
                        throw new ServiceExceptionMappingService().map("Cannot delete {0}", failure,
                                new Path(container, error.getKey(), EnumSet.of(Path.Type.file)));
                    }
                }
            }
            else {
                // Request contains a list of up to 1000 keys that you want to delete
                for(List<ObjectKeyAndVersion> partition : new Partition<ObjectKeyAndVersion>(keys, PreferencesFactory.get().getInteger("s3.delete.multiple.partition"))) {
                    final MultipleDeleteResult result = session.getClient().deleteMultipleObjects(container.getName(),
                            partition.toArray(new ObjectKeyAndVersion[partition.size()]),
                            // Only include errors in response
                            true);
                    if(result.hasErrors()) {
                        for(MultipleDeleteResult.ErrorResult error : result.getErrorResults()) {
                            final ServiceException failure = new ServiceException();
                            failure.setErrorCode(error.getErrorCode());
                            failure.setErrorMessage(error.getMessage());
                            throw new ServiceExceptionMappingService().map("Cannot delete {0}", failure,
                                    new Path(container, error.getKey(), EnumSet.of(Path.Type.file)));
                        }
                    }
                }
            }
        }
        catch(ServiceException e) {
            throw new ServiceExceptionMappingService().map("Cannot delete {0}", e, container);
        }
    }
}
