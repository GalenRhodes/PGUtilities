package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: PGArrays.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 21, 2024
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
import org.jetbrains.annotations.Range;

import java.util.Optional;

public final class PGArrays {
    private PGArrays() { }

    public static <T> Optional<T> get(T @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return Optional.ofNullable((index < array.length) ? array[index] : null);
    }

    public static <T> Optional<T> getFirst(T @NotNull [] array) {
        return get(array, 0);
    }
}
