package com.projectgalen.lib.utils.functions.primitives.toxbi;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: ToShortBiFunctionEx.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 26, 2024
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
 * Represents a function that accepts two arguments and produces an short-valued result.  This is the {@code short}-producing primitive specialization for
 * {@link com.projectgalen.lib.utils.functions.BiFunctionEx}.
 *
 * <p>This is a whose functional method is {@link #applyAsShort(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 *
 * @see com.projectgalen.lib.utils.functions.BiFunctionEx
 */
@FunctionalInterface
public interface ToShortBiFunctionEx<T, U, E extends Exception> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     *
     * @return the function result
     */
    short applyAsShort(T t, U u) throws E;
}
