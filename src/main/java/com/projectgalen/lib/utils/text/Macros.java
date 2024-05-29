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

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public final class Macros {

    private static final @NotNull @NonNls @RegExp String                             MACRO_REGEX = "(?<!\\\\)\\$\\{([^}]+)}";
    private static final                          String                             X           = "\\\\";
    private static final                          String                             Y           = "2️⃣1️⃣1️⃣2️⃣🕑🕐🕐🕑";
    private final @NotNull                        Set<String>                        _nested     = new TreeSet<>();
    private final @NotNull                        Function<String, Optional<String>> _func;
    private final @NotNull                        CharSequence                       _input;

    private Macros() {
        _func  = (__) -> Optional.empty();
        _input = "";
    }

    private Macros(@NotNull CharSequence input, @NotNull Function<String, Optional<String>> func) {
        _func  = func;
        _input = input.toString().replace(X, Y);
    }

    private @NotNull String expand() {
        return Regex.getMatcher(MACRO_REGEX, _input).replaceAll(m -> _func.apply(m.group(1)).filter(s -> !_nested.contains(s)).map(this::foo).orElseGet(m::group)).replace(Y, X);
    }

    private @NotNull String foo(String r) {
        _nested.add(r);
        try { return expand2(r, _func); } finally { _nested.remove(r); }
    }

    public static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, String> func) {
        return expand2(input, s -> ofNullable(func.apply(s)));
    }

    public static @NotNull String expand2(@NotNull CharSequence input, @NotNull Function<String, Optional<String>> func) {
        return new Macros(input, func).expand();
    }
}
