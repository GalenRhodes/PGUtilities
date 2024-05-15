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

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CodePointIterator implements Iterator<CodePointIterator.CodePoint> {
    private final CharSequence chSeq;
    private final int          startIndex;
    private final int          endIndex;
    private       int          index;
    private       int          count = -1;

    public CodePointIterator(@NotNull CharSequence charSequence, int startIndex, int endIndex) {
        super();
        chSeq = charSequence;

        if(startIndex != endIndex) {
            if((startIndex < 0) || (endIndex > chSeq.length()) || (endIndex < startIndex)) throw new IllegalArgumentException("Index out of bounds.");
            if((startIndex > 0) && Character.isLowSurrogate(chSeq.charAt(startIndex)) && Character.isHighSurrogate(chSeq.charAt(startIndex - 1))) --startIndex;
            if((endIndex > startIndex) && (endIndex < chSeq.length()) && Character.isLowSurrogate(chSeq.charAt(endIndex)) && Character.isHighSurrogate(chSeq.charAt(endIndex - 1))) --endIndex;
        }

        this.index      = startIndex;
        this.startIndex = startIndex;
        this.endIndex   = endIndex;
    }

    public synchronized int count() {
        if(count < 0) {
            count = 0;
            int i = startIndex;
            while(i < endIndex) {
                char ch = chSeq.charAt(i++);
                if((i < endIndex) && Character.isHighSurrogate(ch) && Character.isLowSurrogate(chSeq.charAt(i))) ++i;
                ++count;
            }
        }
        return count;
    }

    public @Override boolean hasNext() {
        return (index < endIndex);
    }

    public @Override @NotNull CodePoint next() {
        if(!hasNext()) throw new NoSuchElementException();
        int i  = index;
        int cp = chSeq.charAt(index++);

        if(Character.isHighSurrogate((char)cp) && (index < endIndex)) {
            char c2 = chSeq.charAt(index);
            if(Character.isLowSurrogate(c2)) {
                ++index;
                cp = Character.toCodePoint((char)cp, c2);
            }
        }

        return new CodePoint(cp, i, index);
    }

    public @NotNull CodePoint peek() {
        int       i  = index;
        CodePoint cp = next();
        index = i;
        return cp;
    }

    public record CodePoint(int codePoint, int pos, int next) implements Comparable<CodePointIterator.CodePoint> {
        public @Override int compareTo(@NotNull CodePointIterator.CodePoint o) { return Integer.compare(pos, o.pos); }
    }
}
