package com.projectgalen.lib.utils.concurrency;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: RLock.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 05, 2024
//
// Copyright © 2024 Project Galen. All rights reserved.
//
// Permission to use, copy, modify, and distribute this software for any purpose with or without fee is hereby granted, provided
// that the above copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
// CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
// ================================================================================================================================

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RLock extends ReentrantLock {
    public RLock() { }

    public RLock(boolean fair) {
        super(fair);
    }

    public void doWith(@NotNull Runnable runnable) {
        lock();
        try { runnable.run(); } finally { unlock(); }
    }

    public <T> T getWith(@NotNull Supplier<T> supplier) {
        lock();
        try { return supplier.get(); } finally { unlock(); }
    }
}
