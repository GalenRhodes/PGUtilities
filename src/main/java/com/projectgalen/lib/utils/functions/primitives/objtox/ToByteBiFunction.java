package com.projectgalen.lib.utils.functions.primitives.objtox;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: ToByteBiFunction.java
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
 * Represents a function that accepts two arguments and produces an byte-valued
 * result.  This is the {@code byte}-producing primitive specialization for
 * {@link java.util.function.BiFunction}.
 *
 * <p>This is a whose functional method is {@link #applyAsByte(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 *
 * @see java.util.function.BiFunction
 */
@FunctionalInterface
public interface ToByteBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
     byte applyAsByte(T t, U u);
}
