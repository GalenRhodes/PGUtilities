package com.projectgalen.lib.utils.functions.primitives.xbinaryop;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BooleanBinaryOperator.java
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
 * Represents an operation upon two {@code boolean}-valued operands and producing an
 * {@code boolean}-valued result.   This is the primitive type specialization of
 * {@link java.util.function.BinaryOperator} for {@code boolean}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsBoolean(boolean, boolean)}.
 *
 * @see java.util.function.BinaryOperator
 */
@FunctionalInterface
public interface BooleanBinaryOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     */
    boolean applyAsBoolean(boolean left, boolean right);
}
