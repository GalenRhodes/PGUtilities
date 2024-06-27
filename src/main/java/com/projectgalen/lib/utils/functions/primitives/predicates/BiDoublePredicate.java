package com.projectgalen.lib.utils.functions.primitives.predicates;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BiDoublePredicate.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 25, 2024
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

@SuppressWarnings("unused")
public interface BiDoublePredicate {
    default @NotNull BiDoublePredicate and(@NotNull BiDoublePredicate other) {
        return (t, u) -> test(t, u) && other.test(t, u);
    }

    default BiDoublePredicate negate() {
        return (t, u) -> !test(t, u);
    }

    default @NotNull BiDoublePredicate or(@NotNull BiDoublePredicate other) {
        return (t, u) -> test(t, u) || other.test(t, u);
    }

    boolean test(double t, double u);
}
