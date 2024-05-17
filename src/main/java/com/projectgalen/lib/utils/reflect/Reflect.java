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
import com.projectgalen.lib.utils.annotations.Equals;
import com.projectgalen.lib.utils.annotations.Hash;
import com.projectgalen.lib.utils.functions.BiConsumerEx;
import com.projectgalen.lib.utils.ref.BooleanRef;
import com.projectgalen.lib.utils.stream.Streams;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

@SuppressWarnings("unused")
public final class Reflect {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private static final String GET = "get";
    private static final String SET = "set";

    private Reflect() { }

    public static boolean easyEquals(Object o1, Object o2) {
        return ((o1 == o2) || ((o1 != null) && (o2 != null) && _easyEquals(o1, o2)));
    }

    public static int easyHash(Object o) {
        if(o == null) return 0;
        int      result = 1;
        Class<?> cls    = o.getClass();
        try {
            while(cls != null) {
                for(Field f : cls.getDeclaredFields()) if(f.isAnnotationPresent(Hash.class)) result = ((31 * result) + Objects.hashCode(f.get(o)));
                for(Method m : cls.getDeclaredMethods()) if(isGetter(m) && m.isAnnotationPresent(Hash.class)) result = ((31 * result) + Objects.hashCode(m.invoke(o)));
                cls = cls.getSuperclass();
            }
        }
        catch(IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }

    public static void forEachSuper(Class<?> cls, @NotNull BiConsumer<Class<?>, BooleanRef> consumer) {
        forEachSuperEx(cls, consumer::accept);
    }

    public static <E extends Exception> void forEachSuperEx(Class<?> cls, @NotNull BiConsumerEx<Class<?>, BooleanRef, ? extends E> consumer) throws E {
        BooleanRef stop = new BooleanRef();
        while(cls != null) {
            consumer.accept(cls, stop);
            if(stop.value) break;
            cls = cls.getSuperclass();
        }
    }

    public static Object getFrom(@NotNull AccessibleObject ao, Object obj) throws IllegalAccessException, InvocationTargetException {
        if(obj == null) return null;
        ao.setAccessible(true);
        if(ao instanceof Field f) return f.get(obj);
        if((ao instanceof Method m) && isGetter(m)) return m.invoke(obj);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_getter").formatted(ao.toString()));
    }

    public static boolean isGetter(@NotNull Method m) {
        return ((m.getReturnType() != void.class) && (m.getParameterCount() == 0));
    }

    public static boolean isNamedGetter(@NotNull Method m) {
        return (isGetter(m) && m.getName().startsWith(GET));
    }

    public static boolean isNamedSetter(@NotNull Method m) {
        return (isSetter(m) && m.getName().startsWith(SET));
    }

    public static boolean isSetter(@NotNull Method m) {
        return ((m.getReturnType() == void.class) && (m.getParameterCount() == 1));
    }

    public static <T extends AccessibleObject> T makeAccessible(T ao) {
        if(ao != null) ao.setAccessible(true);
        return ao;
    }

    public static void setTo(@NotNull AccessibleObject ao, Object obj, Object value) throws IllegalAccessException, InvocationTargetException {
        ao.setAccessible(true);
        if(ao instanceof Field f) f.set(obj, value);
        if((ao instanceof Method m) && (m.getReturnType() == void.class) && (m.getParameterCount() == 1)) m.invoke(obj, value);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_setter").formatted(ao.toString()));
    }

    public static @NotNull Stream<Constructor<?>> streamConstructors(@NotNull Class<?> cls) {
        return Stream.of(cls.getDeclaredConstructors()).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<Field> streamFields(Class<?> cls) {
        return streamSupers(cls).flatMap(c -> Stream.of(c.getDeclaredFields())).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<AccessibleObject> streamFieldsAndGetters(@NotNull Class<?> cls) {
        return Streams.build((Builder<AccessibleObject> b) -> forEachSuper(cls, (c, f) -> {
            for(Field fl : c.getDeclaredFields()) b.accept(fl);
            for(Method m : c.getDeclaredMethods()) if(isGetter(m)) b.accept(m);
        })).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<AccessibleObject> streamFieldsAndNamedGetters(@NotNull Class<?> cls) {
        return Streams.build((Builder<AccessibleObject> b) -> forEachSuper(cls, (c, f) -> {
            for(Field fl : c.getDeclaredFields()) b.accept(fl);
            for(Method m : c.getDeclaredMethods()) if(isNamedGetter(m)) b.accept(m);
        })).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<AccessibleObject> streamFieldsAndNamedSetters(@NotNull Class<?> cls) {
        return Streams.build((Builder<AccessibleObject> b) -> forEachSuper(cls, (c, f) -> {
            for(Field fl : c.getDeclaredFields()) b.accept(fl);
            for(Method m : c.getDeclaredMethods()) if(isNamedSetter(m)) b.accept(m);
        })).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<AccessibleObject> streamFieldsAndSetters(@NotNull Class<?> cls) {
        return Streams.build((Builder<AccessibleObject> b) -> forEachSuper(cls, (c, f) -> {
            for(Field fl : c.getDeclaredFields()) b.accept(fl);
            for(Method m : c.getDeclaredMethods()) if(isSetter(m)) b.accept(m);
        })).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<Method> streamGetters(@NotNull Class<?> cls) {
        return streamMethods(cls).filter(Reflect::isGetter);
    }

    public static @NotNull Stream<Method> streamMethods(Class<?> cls) {
        return streamSupers(cls).flatMap(c -> Stream.of(c.getDeclaredMethods())).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<Method> streamPrefixedGetters(@NotNull Class<?> cls) {
        return streamGetters(cls).filter(m -> m.getName().startsWith(GET));
    }

    public static @NotNull Stream<Method> streamPrefixedSetters(@NotNull Class<?> cls) {
        return streamSetters(cls).filter(m -> m.getName().startsWith(SET));
    }

    public static @NotNull Stream<Method> streamSetters(@NotNull Class<?> cls) {
        return streamMethods(cls).filter(Reflect::isSetter);
    }

    public static @NotNull Stream<Class<?>> streamSupers(@NotNull Class<?> cls) {
        return Streams.build(b -> forEachSuper(cls, (c, f) -> b.accept(c)));
    }

    private static boolean _easyEquals(@NotNull Object o1, @NotNull Object o2) {
        Class<?> cls = o1.getClass();
        if(cls != o2.getClass()) return false;

        try {
            while(cls != null) {
                for(Field f : cls.getDeclaredFields()) if(f.isAnnotationPresent(Equals.class) && !Objects.equals(f.get(o1), f.get(o2))) return false;
                for(Method m : cls.getDeclaredMethods()) if(isGetter(m) && m.isAnnotationPresent(Equals.class) && !Objects.equals(m.invoke(o1), m.invoke(o2))) return false;
                cls = cls.getSuperclass();
            }
        }
        catch(IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return true;
    }
}
