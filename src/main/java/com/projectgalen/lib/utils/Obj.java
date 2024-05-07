package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Obj.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 07, 2024
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

import java.util.Comparator;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Obj {

    private Obj() { }

    public static <T> int compare(T a, T b, Comparator<T> comparator) {
        return ((a == b) ? 0 : ((a == null) ? -1 : ((b == null) ? 1 : comparator.compare(a, b))));
    }

    public static <T extends Comparable<T>> int compare(T a, T b) {
        return compare(a, b, Comparator.naturalOrder());
    }

    public static <T, E extends Exception> @NotNull T requireNonNull(T obj, Supplier<E> supplier) throws E {
        if(obj == null) throw supplier.get();
        return obj;
    }
}
