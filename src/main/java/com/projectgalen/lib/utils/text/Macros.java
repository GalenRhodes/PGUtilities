package com.projectgalen.lib.utils.text;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Macros.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 23, 2024
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

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.MatchResult;

public final class Macros {

    private static final @NotNull @NonNls @RegExp String macroRegex = "(?<!\\\\)\\$\\{([^}]+)}";

    private Macros() { }

    public static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, String> func) {
        return expand(input, func, new TreeSet<>());
    }

    private static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, String> func, Set<String> nested) {
        return Regex.getMatcher(macroRegex, input).replaceAll(m -> getExpansion(m, func, nested));
    }

    private static @NotNull String getExpansion(@NotNull MatchResult m, @NotNull Function<String, String> func, @NotNull Set<String> nested) {
        String rep = func.apply(m.group(1));
        if(nested.contains(rep)) return m.group();
        nested.add(rep);
        try { return expand(rep, func, nested); } finally { nested.remove(rep); }
    }
}
