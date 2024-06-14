package com.projectgalen.lib.utils.collections;
// ================================================================================================================================
//     PROJECT: ProxyBuilder
//    FILENAME: XArrayList.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class XArrayList<E> extends ArrayList<E> implements XList<E> {

    public XArrayList() {
        super();
    }

    public XArrayList(@NotNull Collection<? extends E> c) {
        super(c);
    }

    public XArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public @Override @NotNull XArrayList<E> addThen(@NotNull E e) {
        add(e);
        return this;
    }

    public @Override @NotNull XArrayList<E> addIf(@NotNull E e, @NotNull BooleanSupplier predicate) {
        if(predicate.getAsBoolean()) add(e);
        return this;
    }

    public @Override @NotNull XArrayList<E> addWhen(@NotNull E e, @NotNull Predicate<E> predicate) {
        if(predicate.test(e)) add(e);
        return this;
    }

    public @Override @NotNull XArrayList<E> removeAllIf(@NotNull Collection<? extends E> c, @NotNull BooleanSupplier predicate) {
        if(predicate.getAsBoolean()) removeAll(c);
        return this;
    }

    public @Override @NotNull XArrayList<E> removeAllThen(@NotNull Collection<? extends E> c) {
        removeAll(c);
        return this;
    }

    public @Override @NotNull XArrayList<E> removeIf(@NotNull E e, @NotNull BooleanSupplier predicate) {
        if(predicate.getAsBoolean()) remove(e);
        return this;
    }

    public @Override @NotNull XArrayList<E> removeThen(@NotNull E e) {
        remove(e);
        return this;
    }

    public @Override @NotNull XArrayList<E> removeWhen(@NotNull E e, @NotNull Predicate<E> predicate) {
        if(predicate.test(e)) remove(e);
        return this;
    }
}
