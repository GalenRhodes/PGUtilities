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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public final class Macros {

    private final @NotNull Set<String>                        m = new TreeSet<>();
    private final @NotNull Function<String, Optional<String>> f;

    private Macros(@NotNull Function<String, Optional<String>> func) {
        f = func;
    }

    private @NotNull String expand(@NotNull CharSequence input) {
        StringBuilder           sb   = new StringBuilder();
        SimpleCodePointIterator iter = new SimpleCodePointIterator(input);
        while(iter.hasNext()) {
            iter.mark();
            int codePoint = iter.next();
            switch(codePoint) {/*@f0*/
                case '\\' -> append(sb, (iter.hasNext() ? iter.next() : codePoint));
                case '$'  -> sb.append(getMacroName(iter).filter(this::notNested).flatMap(this::getReplacement).orElseGet(iter::getMarked));
                default   -> append(sb, codePoint);
            }/*@f1*/
            iter.releaseMark();
        }
        return sb.toString();
    }

    private @NotNull Optional<String> getMacroName(@NotNull SimpleCodePointIterator iter) {
        iter.mark();
        if(iter.hasNext() && (iter.next() == '{') && iter.hasNext() && (iter.peek() != '}')) {
            StringBuilder sb = append(new StringBuilder(), iter.next());
            while(iter.hasNext()) {
                int codePoint = iter.next();
                switch(codePoint) {/*@f0*/
                    case '$', '{' -> { return f1(iter); }
                    case '}'      -> { return f2(iter, sb); }
                    default       -> append(sb, codePoint);
                }/*@f1*/
            }
        }
        return f1(iter);
    }

    private @NotNull Optional<String> getReplacement(@NotNull String n) {
        m.add(n);
        try { return f.apply(n).map(this::expand); } finally { m.remove(n); }
    }

    private boolean notNested(String n) {
        return !m.contains(n);
    }

    public static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, Optional<String>> func) {
        return new Macros(func).expand(input);
    }

    public static @NotNull String expand2(@NotNull CharSequence input, @NotNull Function<String, String> func) {
        return expand(input, s -> ofNullable(func.apply(s)));
    }

    @Contract("_, _ -> param1") private static @NotNull StringBuilder append(@NotNull StringBuilder sb, int codePoint) {
        return sb.append(Character.toChars(codePoint));
    }

    private static @NotNull Optional<String> f1(@NotNull SimpleCodePointIterator iter) {
        iter.resetMark();
        return Optional.empty();
    }

    private static @NotNull Optional<String> f2(@NotNull SimpleCodePointIterator iter, @NotNull StringBuilder sb) {
        iter.releaseMark();
        return Optional.of(sb.toString());
    }
}
