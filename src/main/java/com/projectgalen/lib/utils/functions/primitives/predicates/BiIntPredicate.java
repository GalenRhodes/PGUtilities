package com.projectgalen.lib.utils.functions.primitives.predicates;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: BiIntPredicate.java
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

public interface BiIntPredicate {
    default @NotNull BiIntPredicate and(@NotNull BiIntPredicate other) {
        return (t, u) -> test(t, u) && other.test(t, u);
    }

    default BiIntPredicate negate() {
        return (t, u) -> !test(t, u);
    }

    default @NotNull BiIntPredicate or(@NotNull BiIntPredicate other) {
        return (t, u) -> test(t, u) || other.test(t, u);
    }

    boolean test(int t, int u);
}