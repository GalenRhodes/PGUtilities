package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Str.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 22, 2024
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

public final class Str {
    private Str() { }

    public static @Contract("null,_->null;!null,_->!null") String leftStr(String str, int len) {
        return ((str == null) ? null : ((len < 0) ? leftStr(str, Math.max(0, (str.length() + len))) : ((len == 0) ? "" : ((len >= str.length()) ? str : str.substring(0, len)))));
    }

    public static @Contract("null,_->null;!null,_->!null") String rightStr(String str, int len) {
        return ((str == null) ? null : ((len < 0) ? rightStr(str, Math.max(0, (str.length() + len))) : ((len == 0) ? "" : ((len >= str.length()) ? str : str.substring(str.length() - len)))));
    }

    public static @Contract("null,_,_->null;!null,_,_->!null") String padLeft(String str, int len, char padding) {
        if((str == null) || (len <= str.length())) return str;
        char[] buffer = PGArrays.newFilled(len, padding);
        str.getChars(0, str.length(), buffer, 0);
        return String.valueOf(buffer);
    }

    public static @Contract("null,_,_->null;!null,_,_->!null") String padRight(String str, int len, char padding) {
        if((str == null) || (len <= str.length())) return str;
        char[] buffer = PGArrays.newFilled(len, padding);
        str.getChars(0, str.length(), buffer, len - str.length());
        return String.valueOf(buffer);
    }

    public static @Contract("null,_,_->null;!null,_,_->!null") String padCenter(String str, int len, char padding) {
        if((str == null) || (len <= str.length())) return str;
        char[] buffer = PGArrays.newFilled(len, padding);
        str.getChars(0, str.length(), buffer, ((len - str.length()) / 2));
        return String.valueOf(buffer);
    }
}
