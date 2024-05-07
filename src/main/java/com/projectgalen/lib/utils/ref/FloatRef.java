package com.projectgalen.lib.utils.ref;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FloatRef.java
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

public class FloatRef implements Comparable<FloatRef> {
    public float value;

    public FloatRef()                                   { this.value = 0; }

    public FloatRef(float value)                        { this.value = value; }

    public @Override int compareTo(@NotNull FloatRef o) { return Float.compare(value, o.value); }

    public @Override boolean equals(Object object)      { return ((this == object) || ((object instanceof CharRef that) && (value == that.value))); }

    public float get()                                  { return value; }

    public float getThenSet(float value) {
        float v = this.value;
        this.value = value;
        return v;
    }

    public @Override int hashCode() { return Objects.hashCode(value); }

    public void set(float value)    { this.value = value; }
}
