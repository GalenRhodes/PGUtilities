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
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
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
        StringBuilder sb = new StringBuilder();
        CPIt          it = new CPIt(input);
        while(it.hasNext()) foo1(sb, it, getNextMarkedCodePoint(it));
        return sb.toString();
    }

    private void foo1(StringBuilder sb, CPIt it, int ch) {
        switch(ch) {
            case '\\' -> append(sb, (it.hasNext() ? it.next() : ch));
            case '$' -> { if(!(it.hasNext() && (it.next() == '{') && it.hasNext() && (it.peek() != '}') && foo2(sb, it, getMacroName(it)))) sb.append(it.getMarked()); }
            default -> append(sb, ch);
        }
    }

    private boolean foo2(@NotNull StringBuilder sb, @NotNull CPIt it, String name) {
        if((name == null) || m.contains(name)) return false;
        m.add(name);
        sb.append(f.apply(name).map(this::expand).orElseGet(it::getMarked));
        m.remove(name);
        return true;
    }

    public static @NotNull String expand(@NotNull CharSequence input, @NotNull Function<String, Optional<String>> func) {
        return new Macros(func).expand(input);
    }

    public static @NotNull String expand2(@NotNull CharSequence input, @NotNull Function<String, String> func) {
        return expand(input, s -> ofNullable(func.apply(s)));
    }

    private static void append(@NotNull StringBuilder sb, int codePoint) {
        sb.append(Character.toChars(codePoint));
    }

    private static @Nullable String getMacroName(@NotNull CPIt it) {
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()) {
            int ch = it.next();
            if(ch == '}') return sb.toString();
            append(sb, ch);
        }
        return null;
    }

    private static int getNextMarkedCodePoint(@NotNull CPIt it) {
        it.mark();
        return it.next();
    }

    private static final class CPIt {
        private final CharSequence input;
        private final int          end;
        private       int          idx;
        private       int          mark;

        CPIt(@NotNull CharSequence input, int start, int end) {
            this.input = input;
            this.idx   = start;
            this.end   = end;
        }

        CPIt(@NotNull CharSequence input) {
            this(input, 0, input.length());
        }

        @NotNull String getMarked() {
            return input.subSequence(mark, end).toString();
        }

        boolean hasNext() {
            return (idx < end);
        }

        void mark() {
            this.mark = this.idx;
        }

        int next() {
            if(idx < end) {
                char c1 = input.charAt(idx++);
                if(Character.isHighSurrogate(c1) && (idx < end)) {
                    char c2 = input.charAt(idx);
                    if(Character.isLowSurrogate(c2)) {
                        ++idx;
                        return Character.toCodePoint(c1, c2);
                    }
                }
                return c1;
            }
            throw new NoSuchElementException();
        }

        int peek() {
            int i = idx;
            int c = next();
            idx = i;
            return c;
        }

        void reset() {
            this.idx = this.mark;
        }
    }
}
