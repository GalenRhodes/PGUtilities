package com.projectgalen.lib.utils.collections;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: ArrayIterator.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 14, 2024
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
import com.projectgalen.lib.utils.Peekable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Markable<T[]>, Peekable<T>, Iterator<T> {
    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private final IntStack      marks = new IntStack(5);
    private final T @NotNull [] array;
    private       int           idx   = 0;

    @SafeVarargs public ArrayIterator(T @NotNull ... array) {
        this.array = Arrays.copyOf(array, array.length);
    }

    @SuppressWarnings("unchecked") public @Override T[] getMarked() {
        if(marks.isEmpty()) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        int i = marks.peek();
        T[] a = (T[])Array.newInstance(array.getClass().componentType(), (idx - i));
        System.arraycopy(array, i, a, 0, a.length);
        return array;
    }

    public @Override boolean hasNext() {
        return (idx < array.length);
    }

    public @Override void mark() {
        marks.push(idx);
    }

    public @Contract("!null->!null") T next(T defaultValue) {
        return hasNext() ? array[idx++] : defaultValue;
    }

    public @Override T next() {
        if(hasNext()) return array[idx++];
        throw new NoSuchElementException();
    }

    public @Override T peek() {
        if(hasNext()) return array[idx];
        throw new NoSuchElementException();
    }

    public @Override void releaseMark() {
        if(marks.isEmpty()) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        marks.pop();
    }

    public @Override void resetMark() {
        if(marks.isEmpty()) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        idx = marks.pop();
    }
}
