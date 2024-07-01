package com.projectgalen.lib.utils.functions.primitives.predicates;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BiLongPredicateEx.java
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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a predicate (boolean-valued function) of two {@code long}-valued arguments.  This is the long-consuming primitive type specialization of {@link java.util.function.BiPredicate}.
 *
 * <p>This is a functional interface whose functional method is {@link #test(long, long)}.
 *
 * @param <E> the type of the exception thrown
 *
 * @see java.util.function.BiPredicate
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface BiLongPredicateEx<E extends Exception> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     * @throws E if an error ocurrs.
     */
    boolean test(long t, long u) throws E;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another.  When evaluating the composed predicate, if this predicate is {@code false}, then
     * the {@code other} predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this predicate throws an exception, the {@code other} predicate will not be
     * evaluated.</p>
     *
     * @param other a predicate that will be logically-ANDed with this predicate

     * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default @NotNull BiLongPredicateEx<? extends E> and(@NotNull BiLongPredicateEx<? extends E> other) {
        return (long t, long u) -> (test(t, u) && other.test(t, u));
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default @NotNull BiLongPredicateEx<? extends E> negate() {
        return (long t, long u) -> (!test(t, u));
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another.  When evaluating the composed predicate, if this predicate is {@code true}, then the
     * {@code other} predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this predicate throws an exception, the {@code other} predicate will not be
     * evaluated.</p>
     *
     * @param other a predicate that will be logically-ORed with this predicate

     * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default @NotNull BiLongPredicateEx<? extends E> or(@NotNull BiLongPredicateEx<? extends E> other) {
        return (long t, long u) -> (test(t, u) || other.test(t, u));
    }
}
