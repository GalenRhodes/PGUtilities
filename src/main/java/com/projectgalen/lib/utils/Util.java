package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Util.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 23, 2024
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

import com.projectgalen.lib.utils.functions.RunnableEx;
import com.projectgalen.lib.utils.functions.SupplierEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class Util {

    private Util() { }

    public static <E extends Exception> void andFinallyEx(@NotNull Runnable before, @NotNull RunnableEx<? extends E> runnable, @NotNull Runnable then) throws E {
        before.run();
        try {
            runnable.run();
        }
        finally {
            then.run();
        }
    }

    public static void andFinally(@NotNull Runnable before, @NotNull Runnable runnable, @NotNull Runnable then) {
        before.run();
        try {
            runnable.run();
        }
        finally {
            then.run();
        }
    }

    public static <R> R getFinally(@NotNull Runnable before, @NotNull Supplier<? extends R> supplier, @NotNull Runnable then) {
        before.run();
        try {
            return supplier.get();
        }
        finally {
            then.run();
        }
    }

    public static <R, E extends Exception> R getFinallyEx(@NotNull Runnable before, @NotNull SupplierEx<? extends R, ? extends E> supplier, @NotNull Runnable then) throws E {
        before.run();
        try {
            return supplier.get();
        }
        finally {
            then.run();
        }
    }
}
