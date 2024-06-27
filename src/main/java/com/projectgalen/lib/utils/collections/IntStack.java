package com.projectgalen.lib.utils.collections;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: IntStack.java
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

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.NoSuchElementException;

@SuppressWarnings("unused")
public class IntStack {
    private       int[]  stack;
    private       int    top;
    private final int    initialCapacity;
    private final Object lock = new Object();

    public IntStack(int initialCapacity) {
        if(initialCapacity < 0) throw new IllegalArgumentException("Initial capacity cannot be less than 0: %d < 0".formatted(initialCapacity));
        this.initialCapacity = initialCapacity;
        this.stack           = new int[initialCapacity];
        this.top             = 0;
    }

    public boolean isEmpty() {
        synchronized(lock) {
            return (top == 0);
        }
    }

    public boolean isNotEmpty() {
        synchronized(lock) {
            return (top > 0);
        }
    }

    public int peek() {
        synchronized(lock) {
            if(top == 0) throw new NoSuchElementException("Stack is empty.");
            return stack[top - 1];
        }
    }

    public int pop() {
        synchronized(lock) {
            if(top == 0) throw new NoSuchElementException("Stack is empty.");
            try {
                return stack[--top];
            }
            finally {
                int lim = (stack.length / 2);
                if((top <= lim) && (lim >= initialCapacity)) {
                    stack = Arrays.copyOf(stack, lim);
                }
            }
        }
    }

    public int pop(int @NotNull [] array, int startingIndex, int length) {
        if(startingIndex < 0 || length < 0 || startingIndex > array.length || length > (array.length - startingIndex)) throw new IndexOutOfBoundsException();
        synchronized(lock) {
            length = Math.min(length, top);
            System.arraycopy(stack, (top - length), array, startingIndex, length);
            top -= length;
            return length;
        }
    }

    public void push(int i) {
        synchronized(lock) {
            if(top == stack.length) stack = ((stack.length == 0) ? new int[128] : Arrays.copyOf(stack, stack.length * 2));
            stack[top++] = i;
        }
    }

    public void push(int @NotNull [] args) {
        push(args, 0, args.length);
    }

    public void push(int @NotNull [] array, int startingIndex, int length) {
        if(startingIndex < 0 || length < 0 || startingIndex > array.length || length > (array.length - startingIndex)) throw new IndexOutOfBoundsException();
        synchronized(lock) {
            int len = stack.length;
            int lim = (len - top);
            if(lim < length) {
                do lim = ((len *= 2) - top); while(lim < length);
                stack = Arrays.copyOf(stack, len);
            }
            System.arraycopy(array, startingIndex, stack, top, length);
            top += length;
        }
    }

    public int size() {
        synchronized(lock) {
            return top;
        }
    }
}
