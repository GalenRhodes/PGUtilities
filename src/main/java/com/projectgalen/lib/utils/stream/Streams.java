package com.projectgalen.lib.utils.stream;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Streams.java
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

import com.projectgalen.lib.utils.stream.iterators.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;

@SuppressWarnings("unused")
public final class Streams {

    public static final int CH = (IMMUTABLE | DISTINCT | NONNULL | ORDERED | SIZED | SUBSIZED | SORTED);

    private Streams() { }

    public static <T> @NotNull Stream<T> build(@NotNull Consumer<Stream.Builder<T>> builder) {
        Stream.Builder<T> b = Stream.builder();
        builder.accept(b);
        return b.build();
    }

    @Contract("_ -> new") public static <T> @NotNull Stream<ArrayIterator.Item<T>> indexStream(T @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static <T> @NotNull Stream<ArrayIterator.Item<T>> indexStream(T @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new ArrayIterator<>(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<BooleanArrayIterator.Item> indexStream(boolean @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<BooleanArrayIterator.Item> indexStream(boolean @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new BooleanArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<CharArrayIterator.Item> indexStream(char @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<CharArrayIterator.Item> indexStream(char @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new CharArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<DoubleArrayIterator.Item> indexStream(double @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<DoubleArrayIterator.Item> indexStream(double @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new DoubleArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<FloatArrayIterator.Item> indexStream(float @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<FloatArrayIterator.Item> indexStream(float @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new FloatArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<IntArrayIterator.Item> indexStream(int @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<IntArrayIterator.Item> indexStream(int @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new IntArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<LongArrayIterator.Item> indexStream(long @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<LongArrayIterator.Item> indexStream(long @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new LongArrayIterator(array, pos, length), length, CH), false);
    }

    @Contract("_ -> new") public static @NotNull Stream<ShortArrayIterator.Item> indexStream(short @NotNull [] array) {
        return indexStream(array, 0, array.length);
    }

    @Contract("_, _, _ -> new") public static @NotNull Stream<ShortArrayIterator.Item> indexStream(short @NotNull [] array, int pos, int length) {
        return StreamSupport.stream(Spliterators.spliterator(new ShortArrayIterator(array, pos, length), length, CH), false);
    }

    public static <T> @NotNull Stream<T> intersection(@NotNull Stream<T> a, @NotNull Stream<T> b) {
        Set<T> set = a.collect(Collectors.toSet());
        return b.filter(set::contains);
    }
}
