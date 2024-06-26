package com.projectgalen.lib.utils.functions.primitives.x2y;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BooleanUnaryOperatorEx.java
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
 * Represents a function that accepts an boolean-valued argument and produces a
 * boolean-valued result.  This is the {@code boolean}-to-{@code boolean} primitive
 * specialization for {@link com.projectgalen.lib.utils.functions.FunctionEx}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsBoolean(boolean)}.
 *
 * @param <E> the type of the thrown exceptions
 *
 * @see com.projectgalen.lib.utils.functions.FunctionEx
 */
@FunctionalInterface
public interface BooleanUnaryOperatorEx<E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E if an error ocurrs.
     */
    boolean applyAsBoolean(boolean value) throws E;
}
