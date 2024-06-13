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

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public final class Macros {

    private static final   String                             VALID = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ._-";
    private final @NotNull Set<String>                        m     = new TreeSet<>();
    private final @NotNull Function<String, Optional<String>> f;

    private Macros(@NotNull Function<String, Optional<String>> func) {
        f = func;
    }

    private @NotNull String expand(@NotNull CharSequence input) {
        StringBuilder sb = new StringBuilder();
        for(SimpleCodePointIterator iter = new SimpleCodePointIterator(input); iter.hasNext(); ) {
            iter.mark();
            int ch = iter.nextCodePoint();
            sb.append((ch == '$') ? getName(iter).filter(n -> !m.contains(n)).flatMap(this::getRepl).orElseGet(iter::getMarked) : toStr(((ch == '\\') && iter.hasNext()) ? iter.nextCodePoint() : ch));
            iter.releaseMark();
        }
        return sb.toString();
    }

    private @NotNull Optional<String> getName(@NotNull SimpleCodePointIterator iter) {
        iter.mark();
        if((iter.hasNext() && (iter.nextCodePoint() == '{') && iter.hasNext() && (iter.peekCodePoint() != '}'))) {
            int           ch = iter.nextCodePoint();
            StringBuilder sb = new StringBuilder().append(toStr(ch));
            while(iter.hasNext() && isValid(ch = iter.nextCodePoint())) sb.append(toStr(ch));
            if(ch == '}') {
                iter.releaseMark();
                return Optional.of(sb.toString());
            }
        }
        iter.resetMark();
        return Optional.empty();
    }

    private @NotNull Optional<String> getRepl(@NotNull String n) {
        m.add(n);
        Optional<String> r = f.apply(n).map(this::expand);
        m.remove(n);
        return r;
    }

    public static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, Optional<String>> func) {
        return new Macros(func).expand(input);
    }

    public static @NotNull String expand2(@NotNull CharSequence input, @NotNull Function<String, String> func) {
        return expand(input, s -> ofNullable(func.apply(s)));
    }

    private static boolean isValid(int ch) {
        return VALID.codePoints().anyMatch(c -> (c == ch));
    }

    private static @NotNull String toStr(int ch) {
        return Character.toString(ch);
    }
}
