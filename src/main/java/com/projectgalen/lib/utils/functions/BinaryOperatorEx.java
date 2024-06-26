package com.projectgalen.lib.utils.functions;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BinaryOperatorEx.java
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

import java.util.function.UnaryOperator;

/**
 * Represents an operation upon two operands of the same type, producing a result of the same type as the operands.  This is a specialization of {@link BiFunctionEx} for the case where the operands
 * and the result are all of the same type.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object, Object)}.</p>
 *
 * @param <T> the type of the operands and result of the operator
 * @param <E> the type of the exception that might be thrown
 *
 * @see BiFunctionEx
 * @see UnaryOperator
 * @since 1.8
 */
@FunctionalInterface
public interface BinaryOperatorEx<T, E extends Exception> extends BiFunctionEx<T, T, T, E> {
    /**
     * Returns a {@link BinaryOperatorEx} which returns the greater of two elements according to the specified {@code Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param <E>        the type of the exception that might be thrown
     * @param comparator a {@code Comparator} for comparing the two values
     *
     * @return a {@code BinaryOperatorEx} which returns the greater of its operands, according to the supplied {@code Comparator}
     *
     * @throws NullPointerException if the argument is null
     */
    public static <T, E extends Exception> BinaryOperatorEx<T, E> maxBy(ComparatorEx<? super T, E> comparator) {
        return (a, b) -> ((comparator.compare(a, b) >= 0) ? a : b);
    }

    /**
     * Returns a {@link BinaryOperatorEx} which returns the lesser of two elements according to the specified {@code Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param <E>        the type of the exception that might be thrown
     * @param comparator a {@code Comparator} for comparing the two values
     *
     * @return a {@code BinaryOperatorEx} which returns the lesser of its operands, according to the supplied {@code Comparator}
     *
     * @throws NullPointerException if the argument is null
     */
    public static <T, E extends Exception> BinaryOperatorEx<T, E> minBy(ComparatorEx<? super T, E> comparator) {
        return (a, b) -> ((comparator.compare(a, b) <= 0) ? a : b);
    }
}
