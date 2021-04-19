package ch.cyberduck.core.cryptomator.features;

/*
 * Copyright (c) 2002-2017 iterate GmbH. All rights reserved.
 * https://cyberduck.io/
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
 */

import ch.cyberduck.core.ListProgressListener;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.Session;
import ch.cyberduck.core.exception.BackgroundException;
import ch.cyberduck.core.features.FileIdProvider;
import ch.cyberduck.core.features.Vault;

public class CryptoFileIdProvider implements FileIdProvider {
    private final Session<?> session;
    private final FileIdProvider delegate;
    private final Vault vault;

    public CryptoFileIdProvider(final Session<?> session, final FileIdProvider delegate, final Vault vault) {
        this.session = session;
        this.delegate = delegate;
        this.vault = vault;
    }

    @Override
    public String getFileId(final Path file, final ListProgressListener listener) throws BackgroundException {
        return delegate.getFileId(vault.encrypt(session, file), listener);
    }

    @Override
    public String cache(final Path file, final String id) throws BackgroundException {
        return delegate.cache(vault.encrypt(session, file), id);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CryptoFileIdProvider{");
        sb.append("delegate=").append(delegate);
        sb.append('}');
        return sb.toString();
    }
}
