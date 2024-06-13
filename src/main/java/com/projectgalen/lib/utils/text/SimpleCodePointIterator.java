package com.projectgalen.lib.utils.text;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: SimpleCodePointIterator.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 31, 2024
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
import com.projectgalen.lib.utils.collections.MarkableIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SimpleCodePointIterator implements Iterator<Integer>, MarkableIterator<String>, Peekable<Integer> {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private final @NotNull CharSequence input;
    private final          int          start;
    private final          int          end;
    private final          int          len;
    private                int          idx;
    private                int[]        marks   = new int[5];
    private                int          markIdx = 0;
    private                int          count   = -1;

    public SimpleCodePointIterator(@NotNull CharSequence input, int startIndex, int endIndex) {
        this.input = input;

        if(startIndex != endIndex) {
            if((startIndex < 0) || (endIndex > input.length()) || (endIndex < startIndex)) throw new IllegalArgumentException(msgs.getString("msg.err.index_oob"));
            if((startIndex > 0) && Character.isLowSurrogate(input.charAt(startIndex)) && Character.isHighSurrogate(input.charAt(startIndex - 1))) --startIndex;
            if((endIndex > startIndex) && (endIndex < input.length()) && Character.isLowSurrogate(input.charAt(endIndex)) && Character.isHighSurrogate(input.charAt(endIndex - 1))) --endIndex;
        }

        this.idx = this.start = startIndex;
        this.end = endIndex;
        this.len = (this.end - this.start);
    }

    public SimpleCodePointIterator(@NotNull CharSequence input, int startIndex) {
        this(input, startIndex, input.length());
    }

    public SimpleCodePointIterator(@NotNull CharSequence input) {
        this(input, 0, input.length());
    }

    public synchronized int count() {
        if(count < 0) count = (int)(input.subSequence(start, end).codePoints().count() & Integer.MAX_VALUE);
        return count;
    }

    public int length() {
        return len;
    }

    public @NotNull CharSequence getInput() {
        return input.subSequence(start, end);
    }

    public @NotNull CharSequence getInput(int startIndex, int endIndex) {
        return input.subSequence(start + startIndex, start + endIndex);
    }

    public @NotNull CharSequence getInput(int startIndex) {
        return input.subSequence((start + startIndex), end);
    }

    public @Override @NotNull String getMarked() {
        return getMarkedSubSequence().toString();
    }

    public @NotNull CharSequence getMarkedSubSequence() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        return input.subSequence(marks[markIdx - 1], idx);
    }

    public @Override boolean hasNext() {
        return (idx < end);
    }

    public @Override void mark() {
        if(markIdx == marks.length) marks = Arrays.copyOf(marks, (marks.length * 2));
        marks[markIdx++] = idx;
    }

    public @Override @NotNull Integer next() {
        return nextInt();
    }

    public int nextInt() {
        int[] n = _next();
        idx = n[1];
        return n[0];
    }

    public @Override @NotNull Integer peek() {
        return peekInt();
    }

    public int peekInt() {
        return _next()[0];
    }

    public @Override void releaseMark() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        --markIdx;
        if((marks.length > 5) && (markIdx <= (marks.length / 2))) marks = Arrays.copyOf(marks, (marks.length / 2));
    }

    public @Override void resetMark() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        idx = marks[--markIdx];
        if((marks.length > 5) && (markIdx <= (marks.length / 2))) marks = Arrays.copyOf(marks, (marks.length / 2));
    }

    private int @NotNull [] _next() {
        if(!hasNext()) throw new NoSuchElementException();
        int  i  = idx;
        char c1 = input.charAt(i++);
        if(Character.isHighSurrogate(c1) && (i < end)) {
            char c2 = input.charAt(i);
            if(Character.isLowSurrogate(c2)) return new int[] { Character.toCodePoint(c1, c2), (i + 1) };
        }
        return new int[] { c1, i };
    }
}
