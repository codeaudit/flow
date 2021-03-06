/*
 * Copyright 2015 Lithium Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lithium.flow.shell.util;

import static com.google.common.base.Preconditions.checkNotNull;

import com.lithium.flow.filer.Filer;
import com.lithium.flow.shell.Exec;
import com.lithium.flow.shell.Shell;
import com.lithium.flow.shell.Tunnel;
import com.lithium.flow.shell.Tunneling;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Nonnull;

/**
 * @author Matt Ayres
 */
public class DecoratedShell implements Shell {
	private final Shell delegate;

	public DecoratedShell(@Nonnull Shell delegate) {
		this.delegate = checkNotNull(delegate);
	}

	@Override
	@Nonnull
	public URI getUri() {
		return delegate.getUri();
	}

	@Override
	@Nonnull
	public Exec exec(@Nonnull String command) throws IOException {
		return delegate.exec(command);
	}

	@Override
	@Nonnull
	public Tunnel tunnel(@Nonnull Tunneling tunneling) throws IOException {
		return delegate.tunnel(tunneling);
	}

	@Override
	@Nonnull
	public Filer getFiler() throws IOException {
		return delegate.getFiler();
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}
}
