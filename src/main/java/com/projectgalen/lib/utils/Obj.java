package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Obj.java
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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Obj {

    private Obj() { }

    @Contract(pure = true) public static boolean allEquals(Object object, Object @NotNull ... others) {
        for(Object other : others) if(!Objects.equals(object, other)) return false;
        return true;
    }

    @Contract(pure = true) public static boolean allEqualsIgnoreCase(String object, String @NotNull ... others) {
        for(String s : others) if(!object.equalsIgnoreCase(s)) return false;
        return true;
    }

    @Contract(pure = true) public static boolean anyEquals(Object object, Object @NotNull ... others) {
        for(Object other : others) if(Objects.equals(object, other)) return true;
        return false;
    }

    @Contract(pure = true) public static boolean anyEqualsIgnoreCase(String object, String @NotNull ... others) {
        for(String s : others) if(object.equalsIgnoreCase(s)) return true;
        return false;
    }

    public static @NotNull Class<?> classForname(@NotNull String name) {
        try {
            return Class.forName(name);
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static <T> int compare(T a, T b, Comparator<T> comparator) {
        return ((a == b) ? 0 : ((a == null) ? -1 : ((b == null) ? 1 : comparator.compare(a, b))));
    }

    public static <T extends Comparable<T>> int compare(T a, T b) {
        return compare(a, b, Comparator.naturalOrder());
    }

    @Contract(pure = true) public static boolean noneEquals(Object object, Object @NotNull ... others) {
        for(Object other : others) if(Objects.equals(object, other)) return false;
        return true;
    }

    @Contract(pure = true) public static boolean noneEqualsIgnoreCase(String object, String @NotNull ... others) {
        for(String s : others) if(object.equalsIgnoreCase(s)) return false;
        return true;
    }

    public static <T, E extends Exception> @NotNull T requireNonNull(T obj, Supplier<E> supplier) throws E {
        if(obj == null) throw supplier.get();
        return obj;
    }
}
