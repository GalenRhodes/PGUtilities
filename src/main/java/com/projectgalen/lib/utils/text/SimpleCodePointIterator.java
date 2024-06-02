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

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.NoSuchElementException;

public final class SimpleCodePointIterator {
    private final CharSequence input;
    private final int          end;
    private       int          idx;
    private       int[]        marks   = new int[5];
    private       int          markIdx = 0;

    public SimpleCodePointIterator(@NotNull CharSequence input, int start, int end) {
        this.input = input;
        this.idx   = start;
        this.end   = end;
    }

    public SimpleCodePointIterator(@NotNull CharSequence input, int start) {
        this(input, start, input.length());
    }

    public SimpleCodePointIterator(@NotNull CharSequence input) {
        this(input, 0, input.length());
    }

    public void releaseMark() {
        if(markIdx == 0) throw new NoSuchElementException();
        --markIdx;
        shrinkStack();
    }

    public @NotNull String getMarked() {
        if(markIdx == 0) throw new NoSuchElementException();
        return input.subSequence(marks[markIdx - 1], idx).toString();
    }

    public boolean hasNext() {
        return (idx < end);
    }

    public void mark() {
        if(markIdx == marks.length) marks = Arrays.copyOf(marks, marks.length * 2);
        marks[markIdx++] = idx;
    }

    public int next() {
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

    public int peek() {
        int i = idx;
        int c = next();
        idx = i;
        return c;
    }

    public void resetMark() {
        if(markIdx == 0) throw new NoSuchElementException();
        idx = marks[--markIdx];
        shrinkStack();
    }

    private void shrinkStack() {
        int i = 5;
        while(i < markIdx) i *= 2;
        marks = Arrays.copyOf(marks, i);
    }
}
