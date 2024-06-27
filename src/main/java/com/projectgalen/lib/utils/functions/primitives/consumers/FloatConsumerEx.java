package com.projectgalen.lib.utils.functions.primitives.consumers;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FloatConsumerEx.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 27, 2024
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

/**
 * Represents an operation that accepts a single {@code float}-valued argument and returns no result.  This is the primitive type specialization of
 * {@link com.projectgalen.lib.utils.functions.ConsumerEx} for {@code float}.  Unlike most other functional interfaces, {@code FloatConsumerEx} is expected to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(float)}.
 *
 * @see com.projectgalen.lib.utils.functions.ConsumerEx
 */
@FunctionalInterface
public interface FloatConsumerEx<E extends Exception> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(float value) throws E;

    /**
     * Returns a composed {@code FloatConsumerEx} that performs, in sequence, this operation followed by the {@code after} operation. If performing either operation throws an exception, it is relayed
     * to the caller of the composed operation.  If performing this operation throws an exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     *
     * @return a composed {@code FloatConsumerEx} that performs in sequence this operation followed by the {@code after} operation
     *
     * @throws NullPointerException if {@code after} is null
     */
    default @NotNull FloatConsumerEx<? extends E> andThen(@NotNull FloatConsumerEx<? extends E> after) {
        return (float t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
