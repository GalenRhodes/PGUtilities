package com.projectgalen.lib.utils.ref;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: CharRef.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 07, 2024
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

import java.util.Objects;

public class CharRef implements Comparable<CharRef> {
    public char value;

    public CharRef()                                   { this.value = 0; }

    public CharRef(char value)                         { this.value = value; }

    @Override public int compareTo(@NotNull CharRef o) { return Character.compare(value, o.value); }

    public @Override boolean equals(Object object)     { return ((this == object) || ((object instanceof CharRef that) && (value == that.value))); }

    public char get()                                  { return value; }

    public char getThenSet(char value) {
        char v = this.value;
        this.value = value;
        return v;
    }

    public @Override int hashCode() { return Objects.hashCode(value); }

    public void set(char value)     { this.value = value; }
}
