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

package com.lithium.flow.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nonnull;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author Matt Ayres
 */
public class Needle<T> {
	private final Threader threader;
	private final BlockingQueue<ListenableFuture<T>> queue = new LinkedBlockingQueue<>();

	public Needle(@Nonnull Threader threader) {
		checkNotNull(threader);
		this.threader = checkNotNull(threader);
	}

	@Nonnull
	public ListenableFuture<T> execute(@Nonnull String name, @Nonnull Executable executable) {
		return submit(name, () -> {
			executable.call();
			return null;
		});
	}

	@Nonnull
	public ListenableFuture<T> submit(@Nonnull String name, @Nonnull Callable<T> callable) {
		checkNotNull(name);
		checkNotNull(callable);

		ListenableFuture<T> future = threader.submit(name, callable);
		queue.add(future);
		return future;
	}

	@Nonnull
	public List<T> finish() {
		List<T> values = new ArrayList<>();

		while (!queue.isEmpty()) {
			List<ListenableFuture<T>> futures = new ArrayList<>();
			queue.drainTo(futures);
			values.addAll(Unchecked.get(() -> Futures.successfulAsList(futures).get()));
		}

		return values;
	}
}
