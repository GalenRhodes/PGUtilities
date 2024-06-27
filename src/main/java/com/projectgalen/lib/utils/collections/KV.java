package com.projectgalen.lib.utils.collections;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: KV.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 15, 2024
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

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class KV<K, V> implements Map.Entry<K, V> {

    protected final @NotNull K key;
    protected                V value;

    public KV(@NotNull K key, V value) {
        this.key   = key;
        this.value = value;
    }

    public @Override boolean equals(Object o) {
        return (this == o) || ((o instanceof KV<?, ?> kv) && Objects.equals(key, kv.key) && Objects.equals(value, kv.value));
    }

    public @Override @NotNull K getKey() {
        return key;
    }

    public @Override V getValue() {
        return value;
    }

    public @Override int hashCode() {
        return Objects.hash(key, value);
    }

    public @Override V setValue(V value) {
        V v = this.value;
        this.value = value;
        return v;
    }
}
