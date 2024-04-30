package com.projectgalen.lib.utils.reflect;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Reflect.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: April 27, 2024
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
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public final class Reflect {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private Reflect() { }

    @SuppressWarnings("unchecked") public static <T> T getFrom(Object obj, @NotNull AccessibleObject ao) throws IllegalAccessException, InvocationTargetException {
        if(obj == null) return null;
        ao.setAccessible(true);
        if(ao instanceof Field f) return (T)f.get(obj);
        if((ao instanceof Method m) && (m.getReturnType() != void.class) && (m.getParameterCount() == 0)) return (T)m.invoke(obj);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_getter").formatted(ao.toString()));
    }

    public static void setTo(@NotNull AccessibleObject ao, @NotNull Object obj, Object value) throws IllegalAccessException, InvocationTargetException {
        ao.setAccessible(true);
        if(ao instanceof Field f) f.set(obj, value);
        if((ao instanceof Method m) && (m.getReturnType() == void.class) && (m.getParameterCount() == 1)) m.invoke(obj, value);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_setter").formatted(ao.toString()));
    }

    public static @NotNull Stream<Constructor<?>> streamDeclaredConstructors(@NotNull Class<?> cls) {
        return Stream.of(cls.getDeclaredConstructors()).peek(m -> m.setAccessible(true));
    }

    public static @NotNull Stream<Field> streamDeclaredFields(Class<?> cls) {
        return streamHierarchy(cls).flatMap(c -> Stream.of(c.getDeclaredFields())).peek(m -> m.setAccessible(true));
    }

    public static @NotNull Stream<Method> streamDeclaredMethods(Class<?> cls) {
        return streamHierarchy(cls).flatMap(c -> Stream.of(c.getDeclaredMethods())).peek(m -> m.setAccessible(true));
    }

    public static @NotNull Stream<? extends AccessibleObject> streamFieldsAndMethods(@NotNull Class<?> cls) {
        return streamHierarchy(cls).flatMap(c -> Stream.concat(Stream.of(c.getDeclaredFields()), Stream.of(c.getDeclaredMethods()))).peek(a -> a.setAccessible(true));
    }

    public static @NotNull Stream<Method> streamGetters(@NotNull Class<?> cls) {
        return streamDeclaredMethods(cls).filter(m -> m.getParameterCount() == 0).filter(m -> m.getReturnType() != void.class);
    }

    public static @NotNull Stream<Class<?>> streamHierarchy(@NotNull Class<?> cls) {
        Builder<Class<?>> builder = Stream.builder();
        Class<?> c = cls;
        while(c != null) {
            builder.accept(c);
            c = c.getSuperclass();
        }
        return builder.build();
    }

    public static @NotNull Stream<Method> streamPrefixedGetters(@NotNull Class<?> cls) {
        return streamGetters(cls).filter(m -> m.getName().startsWith("get"));
    }

    public static @NotNull Stream<Method> streamPrefixedSetters(@NotNull Class<?> cls) {
        return streamSetters(cls).filter(m -> m.getName().startsWith("set"));
    }

    public static @NotNull Stream<Method> streamSetters(@NotNull Class<?> cls) {
        return streamDeclaredMethods(cls).filter(m -> m.getParameterCount() == 1).filter(m -> m.getReturnType() == void.class);
    }
}
