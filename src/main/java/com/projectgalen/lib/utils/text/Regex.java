package com.projectgalen.lib.utils.text;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Regex.java
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

import org.intellij.lang.annotations.MagicConstant;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Regex {

    private static final Map<CacheKey, Pattern> CACHE = new TreeMap<>();

    private Regex() { }

    public static @NotNull Pattern getPattern(@NotNull @NonNls @RegExp String regex) {
        return getPattern(regex, 0);
    }

    public static @NotNull Pattern getPattern(@NotNull String regex, @MagicConstant(flagsFromClass = Pattern.class) int flags) {
        synchronized(CACHE) {
            return CACHE.computeIfAbsent(new CacheKey(regex, flags), k -> Pattern.compile(regex, flags));
        }
    }

    public static @NotNull Matcher getMatcher(@NotNull String regex, @MagicConstant(flagsFromClass = Pattern.class) int flags, @NotNull CharSequence input) {
        return getPattern(regex, flags).matcher(input);
    }

    public static @NotNull Matcher getMatcher(@NotNull String regex, @NotNull CharSequence input) {
        return getPattern(regex, 0).matcher(input);
    }

    private record CacheKey(@NotNull String regex, @MagicConstant(flagsFromClass = Pattern.class) int flags) implements Comparable<CacheKey> {
        public @Override int compareTo(@NotNull Regex.CacheKey o) {
            int c = regex.compareTo(o.regex);
            return ((c == 0) ? Integer.compare(flags, o.flags) : c);
        }
    }
}
