package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: PGArrays.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 21, 2024
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

import com.projectgalen.lib.utils.functions.predicates.*;
import com.projectgalen.lib.utils.functions.primitives.x2y.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.*;

import static java.lang.System.arraycopy;
import static java.lang.reflect.Array.newInstance;
import static java.util.Optional.ofNullable;

@SuppressWarnings({ "unchecked", "unused" })
public final class PGArrays {
    private PGArrays() { }

    public static <T> T @NotNull [] append(@NotNull T src, T @NotNull [] dest) {
        T[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static boolean @NotNull [] append(boolean src, boolean @NotNull [] dest) {
        boolean[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static char @NotNull [] append(char src, char @NotNull [] dest) {
        char[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static byte @NotNull [] append(byte src, byte @NotNull [] dest) {
        byte[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static short @NotNull [] append(short src, short @NotNull [] dest) {
        short[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static int @NotNull [] append(int src, int @NotNull [] dest) {
        int[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static long @NotNull [] append(long src, long @NotNull [] dest) {
        long[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static float @NotNull [] append(float src, float @NotNull [] dest) {
        float[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static double @NotNull [] append(double src, double @NotNull [] dest) {
        double[] array = Arrays.copyOf(dest, dest.length + 1);
        array[dest.length] = src;
        return array;
    }

    public static <T> T[] concat(T @NotNull [] a1, T @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static boolean @NotNull [] concat(boolean @NotNull [] a1, boolean @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static char @NotNull [] concat(char @NotNull [] a1, char @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static byte @NotNull [] concat(byte @NotNull [] a1, byte @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static short @NotNull [] concat(short @NotNull [] a1, short @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static int @NotNull [] concat(int @NotNull [] a1, int @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static long @NotNull [] concat(long @NotNull [] a1, long @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static float @NotNull [] concat(float @NotNull [] a1, float @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static double @NotNull [] concat(double @NotNull [] a1, double @NotNull [] a2) {
        return copy(a2, 0, Arrays.copyOf(a1, a1.length + a2.length), a1.length, a2.length);
    }

    public static @Contract("_, _, _, _, _ -> param3") <T> T[] copy(T @NotNull [] src, int srcPos, T @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") boolean @NotNull [] copy(boolean @NotNull [] src, int srcPos, boolean @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") char @NotNull [] copy(char @NotNull [] src, int srcPos, char @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") byte @NotNull [] copy(byte @NotNull [] src, int srcPos, byte @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") short @NotNull [] copy(short @NotNull [] src, int srcPos, short @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") int @NotNull [] copy(int @NotNull [] src, int srcPos, int @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") long @NotNull [] copy(long @NotNull [] src, int srcPos, long @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") float @NotNull [] copy(float @NotNull [] src, int srcPos, float @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static @Contract("_, _, _, _, _ -> param3") double @NotNull [] copy(double @NotNull [] src, int srcPos, double @NotNull [] dest, int destPos, int length) {
        if(length > 0) arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static <T> boolean equals(T[] a1, T[] a2, @NotNull BiPredicate<T, T> predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(boolean[] a1, boolean[] a2, @NotNull BiBooleanPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(char[] a1, char[] a2, @NotNull BiCharPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(byte[] a1, byte[] a2, @NotNull BiBytePredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(short[] a1, short[] a2, @NotNull BiShortPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(int[] a1, int[] a2, @NotNull BiIntPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(long[] a1, long[] a2, @NotNull BiLongPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(float[] a1, float[] a2, @NotNull BiFloatPredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static boolean equals(double[] a1, double[] a2, @NotNull BiDoublePredicate predicate) {
        if(a1 == a2) return true;
        if((a1 == null) || (a2 == null) || (a1.length != a2.length)) return false;
        for(int i = 0; i < a1.length; ++i) if(!predicate.test(a1[i], a2[i])) return false;
        return true;
    }

    public static <T> @NotNull Optional<T> get(T @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Boolean> get(boolean @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Character> get(char @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Byte> get(byte @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Short> get(short @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Integer> get(int @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Long> get(long @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Float> get(float @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static @NotNull Optional<Double> get(double @NotNull [] array, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return ofNullable((index < array.length) ? array[index] : null);
    }

    public static <T> @NotNull Optional<T> getFirst(T @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Boolean> getFirst(boolean @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Character> getFirst(char @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Byte> getFirst(byte @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Short> getFirst(short @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Integer> getFirst(int @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Long> getFirst(long @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Float> getFirst(float @NotNull [] array) {
        return get(array, 0);
    }

    public static @NotNull Optional<Double> getFirst(double @NotNull [] array) {
        return get(array, 0);
    }

    public static <T> T[] newFilled(@NotNull Class<T> componentType, int length, T fill) {
        T[] array = (T[])newInstance(componentType, length);
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static boolean @NotNull [] newFilled(int length, boolean fill) {
        boolean[] array = new boolean[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static char @NotNull [] newFilled(int length, char fill) {
        char[] array = new char[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static byte @NotNull [] newFilled(int length, byte fill) {
        byte[] array = new byte[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static short @NotNull [] newFilled(int length, short fill) {
        short[] array = new short[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static int @NotNull [] newFilled(int length, int fill) {
        int[] array = new int[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static long @NotNull [] newFilled(int length, long fill) {
        long[] array = new long[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static float @NotNull [] newFilled(int length, float fill) {
        float[] array = new float[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static double @NotNull [] newFilled(int length, double fill) {
        double[] array = new double[length];
        if(length > 0) Arrays.fill(array, fill);
        return array;
    }

    public static <T> T[] prepend(@NotNull T src, T @NotNull [] dest) {
        T[] cp = (T[])newInstance(dest.getClass().componentType(), dest.length + 1);
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static boolean @NotNull [] prepend(boolean src, boolean @NotNull [] dest) {
        boolean[] cp = new boolean[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static char @NotNull [] prepend(char src, char @NotNull [] dest) {
        char[] cp = new char[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static byte @NotNull [] prepend(byte src, byte @NotNull [] dest) {
        byte[] cp = new byte[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static short @NotNull [] prepend(short src, short @NotNull [] dest) {
        short[] cp = new short[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static int @NotNull [] prepend(int src, int @NotNull [] dest) {
        int[] cp = new int[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static long @NotNull [] prepend(long src, long @NotNull [] dest) {
        long[] cp = new long[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static float @NotNull [] prepend(float src, float @NotNull [] dest) {
        float[] cp = new float[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static double @NotNull [] prepend(double src, double @NotNull [] dest) {
        double[] cp = new double[dest.length + 1];
        cp[0] = src;
        return copy(dest, 0, cp, 1, dest.length);
    }

    public static <T> T @NotNull [] setAll(T @NotNull [] dest, @NotNull IntFunction<? extends T> generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.apply(i);
        return dest;
    }

    public static boolean @NotNull [] setAll(boolean @NotNull [] dest, @NotNull IntToBooleanFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsBoolean(i);
        return dest;
    }

    public static char @NotNull [] setAll(char @NotNull [] dest, @NotNull IntToCharFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsChar(i);
        return dest;
    }

    public static byte @NotNull [] setAll(byte @NotNull [] dest, @NotNull IntToByteFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsByte(i);
        return dest;
    }

    public static short @NotNull [] setAll(short @NotNull [] dest, @NotNull IntToShortFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsShort(i);
        return dest;
    }

    public static int @NotNull [] setAll(int @NotNull [] dest, @NotNull IntUnaryOperator generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsInt(i);
        return dest;
    }

    public static long @NotNull [] setAll(long @NotNull [] dest, @NotNull IntToLongFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsLong(i);
        return dest;
    }

    public static float @NotNull [] setAll(float @NotNull [] dest, @NotNull IntToFloatFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsFloat(i);
        return dest;
    }

    public static double @NotNull [] setAll(double @NotNull [] dest, @NotNull IntToDoubleFunction generator) {
        for(int i = 0; i < dest.length; ++i) dest[i] = generator.applyAsDouble(i);
        return dest;
    }

    @Contract(value = "_ -> new", pure = true) public static <T> T @NotNull [] wrap(T... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static boolean @NotNull [] wrap(boolean... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static char @NotNull [] wrap(char... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static byte @NotNull [] wrap(byte... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static short @NotNull [] wrap(short... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static int @NotNull [] wrap(int... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static long @NotNull [] wrap(long... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static float @NotNull [] wrap(float... items) {
        return Arrays.copyOf(items, items.length);
    }

    @Contract(value = "_ -> new", pure = true) public static double @NotNull [] wrap(double... items) {
        return Arrays.copyOf(items, items.length);
    }
}
