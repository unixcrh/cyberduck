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

import ch.cyberduck.core.AsciiRandomStringService;
import ch.cyberduck.core.Credentials;
import ch.cyberduck.core.DisabledHostKeyCallback;
import ch.cyberduck.core.DisabledLoginCallback;
import ch.cyberduck.core.Host;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.TranscriptListener;
import ch.cyberduck.core.features.Delete;
import ch.cyberduck.test.IntegrationTest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;
import java.util.EnumSet;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
public class S3BucketCreateServiceTest {

    @Test
    public void testCreateBucket() throws Exception {
        final S3Session session = new S3Session(
            new Host(new S3Protocol(), new S3Protocol().getDefaultHostname(),
                new Credentials(
                    System.getProperties().getProperty("s3.key"), System.getProperties().getProperty("s3.secret")
                )));
        session.withListener(new TranscriptListener() {
            @Override
            public void log(final Type request, final String message) {
                assertFalse(StringUtils.containsIgnoreCase(message, "expect"));
            }
        });
        assertNotNull(session.open(new DisabledHostKeyCallback()));
        final S3FindFeature find = new S3FindFeature(session);
        final S3DefaultDeleteFeature delete = new S3DefaultDeleteFeature(session);
        final S3BucketCreateService create = new S3BucketCreateService(session);
        final Path bucket = new Path(new AsciiRandomStringService().random(), EnumSet.of(Path.Type.directory, Path.Type.volume));
        create.create(bucket, "eu-central-1");
        bucket.attributes().setRegion("eu-central-1");
        assertTrue(find.find(bucket));
        delete.delete(Collections.<Path>singletonList(bucket), new DisabledLoginCallback(), new Delete.DisabledCallback());
        assertFalse(find.find(bucket));
        session.close();
    }
}
