package com.projectgalen.lib.utils.functions.primitives.x2y;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FloatToShortFunction.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: July 01, 2024
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

/**
 * Represents a function that accepts an float-valued argument and produces a
 * short-valued result.  This is the {@code float}-to-{@code short} primitive
 * specialization for {@link java.util.function.Function}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsShort(float)}.
 *
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface FloatToShortFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    short applyAsShort(float value);
}
