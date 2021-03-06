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

package com.lithium.flow.shell;

import static java.util.stream.Collectors.toList;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.google.common.base.Joiner;

/**
 * @author Matt Ayres
 */
public interface Exec extends Closeable {
	@Nonnull
	Stream<String> out();

	@Nonnull
	Stream<String> err();

	@Nonnull
	Optional<Integer> exit() throws IOException;

	@Nonnull
	default String line() throws IOException {
		try {
			return Joiner.on('\n').join(out().collect(toList()));
		} finally {
			close();
		}
	}
}
