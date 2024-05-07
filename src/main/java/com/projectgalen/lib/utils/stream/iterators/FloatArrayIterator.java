package com.projectgalen.lib.utils.stream.iterators;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FloatArrayIterator.java
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

import com.projectgalen.lib.utils.PGResourceBundle;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unused")
public class FloatArrayIterator implements Iterator<FloatArrayIterator.Item> {
    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private final float[] array;
    private       int     idx;
    private final int     limit;

    public FloatArrayIterator(float @NotNull ... array) {
        this(array, 0, array.length);
    }

    public FloatArrayIterator(float @NotNull [] array, int pos, int length) {
        this.array = array;
        this.idx   = pos;
        this.limit = (pos + length);
        if((idx < 0) || (idx > array.length)) throw new IllegalArgumentException(msgs.getString("msg.err.start_pos_oob"));
        if((length < 0) || (limit > array.length)) throw new IllegalArgumentException(msgs.getString("msg.err.len_oob"));
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next} would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    public @Override boolean hasNext() {
        return (idx < limit);
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     *
     * @throws NoSuchElementException if the iteration has no more elements
     */
    public @Override FloatArrayIterator.Item next() {
        if(!hasNext()) throw new NoSuchElementException();
        int i = idx++;
        return new Item(i, array[i]);
    }

    public record Item(int idx, float value) implements Comparable<Item> {
        public @Override int compareTo(@NotNull Item o) { return Integer.compare(idx, o.idx); }
    }
}
