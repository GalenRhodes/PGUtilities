package com.projectgalen.lib.utils.functions.primitives.consumers;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: ObjShortConsumerEx.java
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
 * Represents an operation that accepts an object-valued and a
 * {@code short}-valued argument, and returns no result.  This is the
 * {@code (reference, short)} specialization of {@link com.projectgalen.lib.utils.functions.BiConsumerEx}.
 * Unlike most other functional interfaces, {@code ObjShortConsumerEx} is
 * expected to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(Object, short)}.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of the thrown exceptions
 *
 * @see com.projectgalen.lib.utils.functions.BiConsumerEx
 */
@FunctionalInterface
public interface ObjShortConsumerEx<T, E extends Exception> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param value the second input argument
     * @throws E if an error ocurrs.
     */
    void accept(T t, short value) throws E;
}
