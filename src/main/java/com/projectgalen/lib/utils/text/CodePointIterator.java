package com.projectgalen.lib.utils.text;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: CodePointIterator.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 15, 2024
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
import com.projectgalen.lib.utils.collections.Markable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CodePointIterator implements Iterator<CodePointIterator.CodePoint>, Markable<String>, Peekable<CodePointIterator.CodePoint> {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private final CharSequence input;
    private final int          endIndex;
    private       int          index;
    private       int          count   = -1;
    private       int[]        marks   = new int[5];
    private       int          markIdx = 0;

    public CodePointIterator(@NotNull CharSequence charSequence, int startIndex, int endIndex) {
        super();
        input = charSequence;

        if(startIndex != endIndex) {
            if((startIndex < 0) || (endIndex > input.length()) || (endIndex < startIndex)) throw new IllegalArgumentException(msgs.getString("msg.err.index_oob"));
            if((startIndex > 0) && Character.isLowSurrogate(input.charAt(startIndex)) && Character.isHighSurrogate(input.charAt(startIndex - 1))) --startIndex;
            if((endIndex > startIndex) && (endIndex < input.length()) && Character.isLowSurrogate(input.charAt(endIndex)) && Character.isHighSurrogate(input.charAt(endIndex - 1))) --endIndex;
        }

        this.index    = startIndex;
        this.endIndex = endIndex;
    }

    public CodePointIterator(@NotNull CharSequence charSequence, int startIndex) {
        this(charSequence, startIndex, charSequence.length());
    }

    public CodePointIterator(@NotNull CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public synchronized int count() {
        if(count < 0) count = (int)(input.codePoints().count() & Integer.MAX_VALUE);
        return count;
    }

    public @Override String getMarked() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        return "";
    }

    public @Override boolean hasNext() {
        return (index < endIndex);
    }

    public @Override void mark() {
        if(markIdx == marks.length) marks = Arrays.copyOf(marks, (marks.length * 2));
        marks[markIdx++] = index;
    }

    public @Override @NotNull CodePoint next() {
        CodePoint cp = peek();
        index = cp.next;
        return cp;
    }

    public @Override @NotNull CodePoint peek() {
        if(!hasNext()) throw new NoSuchElementException();
        int  i  = index;
        char c1 = input.charAt(i++);

        if(Character.isHighSurrogate(c1) && (i < endIndex)) {
            char c2 = input.charAt(i);
            if(Character.isLowSurrogate(c2)) return new CodePoint(Character.toCodePoint(c1, c2), index, i + 1);
        }
        return new CodePoint(c1, index, i);
    }

    public @Override void releaseMark() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        --markIdx;
        if((marks.length > 5) && (markIdx <= (marks.length / 2))) marks = Arrays.copyOf(marks, (marks.length / 2));
    }

    public @Override void resetMark() {
        if(markIdx == 0) throw new NoSuchElementException(msgs.getString("msg.err.no_mark_found"));
        index = marks[--markIdx];
        if((marks.length > 5) && (markIdx <= (marks.length / 2))) marks = Arrays.copyOf(marks, (marks.length / 2));
    }

    public record CodePoint(int codePoint, int pos, int next) implements Comparable<CodePointIterator.CodePoint> {
        public @Override int compareTo(@NotNull CodePointIterator.CodePoint o) { return Integer.compare(pos, o.pos); }
    }
}
