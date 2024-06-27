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

import com.projectgalen.lib.utils.PGArrays;
import com.projectgalen.lib.utils.PGResourceBundle;
import com.projectgalen.lib.utils.annotations.Equals;
import com.projectgalen.lib.utils.annotations.Hash;
import com.projectgalen.lib.utils.functions.BiConsumerEx;
import com.projectgalen.lib.utils.ref.BooleanRef;
import com.projectgalen.lib.utils.stream.Streams;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import static com.projectgalen.lib.utils.PGArrays.getFirst;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public final class Reflect {

    private static final PGResourceBundle msgs      = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final String           GET       = "get";
    private static final String           SET       = "set";
    private static final Class<?>[]       NO_PARAMS = new Class<?>[0];

    private Reflect() { }

    public static boolean compareParams(Class<?> @NotNull [] parameterTypes, @NotNull Constructor<?> c) {
        return PGArrays.equals(parameterTypes, c.getParameterTypes(), (t1, t2) -> (t1 == t2));
    }

    public static boolean compareParams(Class<?> @NotNull [] parameterTypes, @NotNull Method m) {
        return PGArrays.equals(parameterTypes, m.getParameterTypes(), (t1, t2) -> (t1 == t2));
    }

    public static boolean easyEquals(Object o1, Object o2) {
        return ((o1 == o2) || ((o1 != null) && (o2 != null) && _easyEquals(o1, o2)));
    }

    public static int easyHash(Object o) {
        return ((o == null) ? 0 : _easyHash(o));
    }

    public static boolean equals(@NotNull AccessibleObject ao, Object o1, Object o2) {
        return Objects.equals(ofNullable(o1).map(o -> getFrom(ao, o)).orElse(null), ofNullable(o2).map(o -> getFrom(ao, o)).orElse(null));
    }

    public static @NotNull Stream<Method> findMethods(@NotNull Class<?> cls, @NotNull String name, Class<?>... paramTypes) {
        return streamMethods(cls).filter(m -> name.equals(m.getName()) && compareParams(paramTypes, m));
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

    public static Optional<Class<?>> getAccessibleObjectTypeForGet(@NotNull AccessibleObject ao) {
        return ofNullable((ao instanceof Field f) ? f.getType() : ((ao instanceof Method m) ? m.getReturnType() : null));
    }

    public static Optional<Class<?>> getAccessibleObjectTypeForSet(@NotNull AccessibleObject ao) {
        return ofNullable((ao instanceof Field f) ? f.getType() : ((ao instanceof Method m) ? getFirst(m.getParameters()).map(Parameter::getType).orElse(null) : null));
    }

    public static @SuppressWarnings("unchecked") @Nullable <T> Constructor<T> getConstructor(@NotNull Class<T> cls, Class<?>... parameterTypes) {
        return (Constructor<T>)stream(cls.getDeclaredConstructors()).filter(c -> compareParams(parameterTypes, c)).findFirst().orElse(null);
    }

    public static Object getFrom(@NotNull AccessibleObject ao, Object obj) {
        if(ao instanceof Field f) return getFromField(f, obj);
        if(ao instanceof Method m) return getFromGetter(m, obj);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_getter").formatted(ao));
    }

    public static Object getFromField(@NotNull Field f, Object obj) {
        try {
            if((obj == null) && !isStatic(f)) throw new IllegalArgumentException(msgs.getString("msg.err.not_static_field").formatted(f));
            f.setAccessible(true);
            return f.get(obj);
        }
        catch(IllegalAccessException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static Object getFromGetter(@NotNull Method m, Object obj) {
        if(!isGetter(m)) throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_getter").formatted(m));
        return invoke(m, obj);
    }

    public static <T> @NotNull T getInstance(@NotNull Class<T> cls) {
        return getInstance(cls, NO_PARAMS);
    }

    public static <T> @NotNull T getInstance(@NotNull Class<T> cls, Class<?>[] paramTypes, Object... params) {
        try {
            return ofNullable(getConstructor(cls, ofNullable(paramTypes).orElse(NO_PARAMS))).map(Reflect::makeAccessible).orElseThrow(RuntimeException::new).newInstance(params);
        }
        catch(InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is similar to {@link Class#getDeclaredMethod(String, Class[])} except that <b><i>1)</i></b> it also looks at inherited methods as well as declared methods and <b><i>2)</i></b> if a
     * method is not found that matches then this method simply returns {@code null} rather than throwing a {@link NoSuchMethodException}.
     *
     * @param cls            The class to search for the method.
     * @param name           The name of the method to search for.
     * @param parameterTypes The parameters types.
     *
     * @return The matching method or {@code null} if no match is found.
     */
    public static @Nullable Method getMethod(@NotNull Class<?> cls, @NotNull String name, Class<?> @NotNull ... parameterTypes) {
        return getMethod(cls, false, name, parameterTypes);
    }

    /**
     * This method is similar to {@link Class#getDeclaredMethod(String, Class[])} except that <b><i>1)</i></b> it also looks at inherited methods as well as declared methods and <b><i>2)</i></b> if a
     * method is not found that matches then this method simply returns {@code null} rather than throwing a {@link NoSuchMethodException}.
     *
     * @param cls        The class to search for the method.
     * @param isStatic   If {@code true}, only static methods will be searched for.
     * @param name       The name of the method to search for.
     * @param paramTypes The parameters types.
     *
     * @return The matching method or {@code null} if no match is found.
     */
    public static @Nullable Method getMethod(@NotNull Class<?> cls, boolean isStatic, @NotNull String name, Class<?> @NotNull ... paramTypes) {
        return streamMethods(cls).filter(m -> ((isStatic == isStatic(m)) && name.equals(m.getName()) && compareParams(paramTypes, m))).findFirst().orElse(null);
    }

    public static @NotNull Class<?> getNonPrimitiveType(@NotNull Class<?> cls) {
        if(!cls.isPrimitive())   return cls;/*@f0*/
        if(cls == boolean.class) return Boolean.class;
        if(cls == char.class)    return Character.class;
        if(cls == byte.class)    return Byte.class;
        if(cls == short.class)   return Short.class;
        if(cls == int.class)     return Integer.class;
        if(cls == long.class)    return Long.class;
        if(cls == float.class)   return Float.class;
        if(cls == double.class)  return Double.class;
        return cls;/*@f1*/
    }

    public static @NotNull Class<?> getPrimitiveType(@NotNull Class<?> cls) {
        if(cls.isPrimitive())      return cls;/*@f0*/
        if(cls == Boolean.class)   return boolean.class;
        if(cls == Character.class) return char.class;
        if(cls == Byte.class)      return byte.class;
        if(cls == Short.class)     return short.class;
        if(cls == Integer.class)   return int.class;
        if(cls == Long.class)      return long.class;
        if(cls == Float.class)     return float.class;
        if(cls == Double.class)    return double.class;
        return cls;/*@f1*/
    }

    public static @Contract(pure = true) @SafeVarargs boolean hasAll(@NotNull AnnotatedElement annotatedElement, Class<? extends Annotation> @NotNull ... annotations) {
        return stream(annotations).map(annotatedElement::isAnnotationPresent).filter(b -> !b).findFirst().orElse(true);
    }

    public static @Contract(pure = true) @SafeVarargs boolean hasAny(@NotNull AnnotatedElement annotatedElement, Class<? extends Annotation> @NotNull ... annotations) {
        return stream(annotations).map(annotatedElement::isAnnotationPresent).filter(b -> b).findFirst().orElse(false);
    }

    public static @SuppressWarnings({ "unchecked", "UnusedReturnValue" }) <T> T invoke(@NotNull Method method, @Nullable Object target, Object... params) {
        try {
            if((target == null) && !isStatic(method)) throw new IllegalArgumentException(msgs.getString("msg.err.not_static_method").formatted(method));
            method.setAccessible(true);
            return (T)method.invoke(target, params);
        }
        catch(InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static boolean isAbstract(@NotNull Member m) {
        return Modifier.isAbstract(m.getModifiers());
    }

    /**
     * This method tests to see if a field or parameter of type {@code target} is assignable with a value of type {@code source}. This method also takes into account numeric values that can be
     * assigned without having to cast the values. For example, a value of type {@code int} can be assigned to a field or parameter of type {@code long} but not visa-versa.
     *
     * @param target The type of the target (i.e. a field or a parameter).
     * @param source The type of the source.
     *
     * @return {@code true} if source can be assigned to target. {@code false} otherwise.
     */
    public static boolean isAssignable(@NotNull Class<?> target, @NotNull Class<?> source) {
        if((target == source) || target.isAssignableFrom(source)) return true;
        Class<?> pTarget = getPrimitiveType(target);
        Class<?> pSource = getPrimitiveType(source);
        /*@f0*/
        return (pTarget.isPrimitive() && pSource.isPrimitive() && (((pTarget == pSource) || ((pTarget == double.class) && (pSource != boolean.class)))
                || ((pTarget == float.class) && ((pSource == long.class)  || (pSource == int.class)   || (pSource == short.class) || (pSource == byte.class) || (pSource == char.class)))
                || ((pTarget == long.class)  && ((pSource == int.class)   || (pSource == short.class) || (pSource == byte.class)  || (pSource == char.class)))
                || ((pTarget == int.class)   && ((pSource == short.class) || (pSource == byte.class)  || (pSource == char.class)))
                || ((pTarget == short.class) && (pSource == byte.class))));
    }/*@f1*/

    public static boolean isBoolean(@NotNull Class<?> cls) {
        return ((cls == Boolean.class) || (cls == boolean.class));
    }

    public static boolean isCharacter(@NotNull Class<?> cls) {
        return ((cls == char.class) || (cls == Character.class));
    }

    public static boolean isFinal(@NotNull Member m) {
        return Modifier.isFinal(m.getModifiers());
    }

    public static boolean isGetter(@NotNull Method m) {
        return ((m.getReturnType() != void.class) && (m.getParameterCount() == 0));
    }

    public static boolean isInterface(@NotNull Member m) {
        return Modifier.isInterface(m.getModifiers());
    }

    public static boolean isNamedGetter(@NotNull Method m) {
        return (isGetter(m) && m.getName().startsWith(GET));
    }

    public static boolean isNamedSetter(@NotNull Method m) {
        return (isSetter(m) && m.getName().startsWith(SET));
    }

    public static boolean isNative(@NotNull Member m) {
        return Modifier.isNative(m.getModifiers());
    }

    public static boolean isNumeric(@NotNull Class<?> cls) {
        return ((cls == int.class)/*@f0*/
                || (cls == Integer.class)
                || (cls == long.class)
                || (cls == Long.class)
                || (cls == double.class)
                || (cls == Double.class)
                || (cls == byte.class)
                || (cls == Byte.class)
                || (cls == short.class)
                || (cls == Short.class)
                || (cls == float.class)
                || (cls == Float.class));/*@f1*/
    }

    public static boolean isNumericOrChar(@NotNull Class<?> cls) {
        return (isCharacter(cls) || isNumeric(cls));
    }

    public static boolean isPrivate(@NotNull Member m) {
        return Modifier.isPrivate(m.getModifiers());
    }

    public static boolean isProtected(@NotNull Member m) {
        return Modifier.isProtected(m.getModifiers());
    }

    public static boolean isPublic(@NotNull Member m) {
        return Modifier.isPublic(m.getModifiers());
    }

    public static boolean isSetter(@NotNull Method m) {
        return (isVoid(m.getReturnType()) && (m.getParameterCount() == 1));
    }

    public static boolean isStatic(@NotNull Member m) {
        return Modifier.isStatic(m.getModifiers());
    }

    public static boolean isStrict(@NotNull Member m) {
        return Modifier.isStrict(m.getModifiers());
    }

    public static boolean isSynchronized(@NotNull Member m) {
        return Modifier.isSynchronized(m.getModifiers());
    }

    public static boolean isTransient(@NotNull Member m) {
        return Modifier.isTransient(m.getModifiers());
    }

    public static boolean isVoid(@NotNull Class<?> cls) {
        return ((cls == void.class) || (cls == Void.class));
    }

    public static boolean isVolatile(@NotNull Member m) {
        return Modifier.isVolatile(m.getModifiers());
    }

    public static <T extends AccessibleObject> T makeAccessible(T ao) {
        if(ao != null) ao.setAccessible(true);
        return ao;
    }

    public static <T, U extends T> @NotNull Optional<T> safeCast(@NotNull Class<T> cls, U obj) {
        return ofNullable(obj).filter(o -> cls.isInstance(obj)).map(cls::cast);
    }

    public static void setTo(@NotNull AccessibleObject ao, Object obj, Object value) {
        if(ao instanceof Field f) setToField(f, obj, value);
        if(ao instanceof Method m) setToSetter(m, obj, value);
        throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_setter").formatted(ao.toString()));
    }

    public static void setToField(@NotNull Field field, Object obj, Object value) {
        try {
            if((obj == null) && !isStatic(field)) throw new IllegalArgumentException(msgs.getString("msg.err.not_static_field").formatted(field));
            if(isFinal(field)) throw new IllegalArgumentException(msgs.getString("msg.err.field_is_final").formatted(field));
            field.setAccessible(true);
            field.set(obj, value);
        }
        catch(IllegalAccessException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static void setToSetter(@NotNull Method method, Object obj, Object value) {
        if(!isSetter(method)) throw new IllegalArgumentException(msgs.getString("msg.err.ao_not_field_or_setter").formatted(method));
        invoke(method, obj, value);
    }

    public static @NotNull Stream<Constructor<?>> streamConstructors(@NotNull Class<?> cls) {
        return stream(cls.getDeclaredConstructors()).map(Reflect::makeAccessible);
    }

    public static @NotNull Stream<Field> streamFields(Class<?> cls) {
        return streamSupers(cls).flatMap(c -> stream(c.getDeclaredFields()));
    }

    public static @NotNull Stream<AccessibleObject> streamFieldsAndGetters(@NotNull Class<?> cls) {
        return Streams.build((Builder<AccessibleObject> b) -> forEachSuper(cls, (c, f) -> {
            for(Field fl : c.getDeclaredFields()) b.accept(fl);
            for(Method m : c.getDeclaredMethods()) if(isGetter(m)) b.accept(m);
        }));
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
        return streamSupers(cls).flatMap(c -> stream(c.getDeclaredMethods())).peek(ao -> ao.setAccessible(true));
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
        return ((o1.getClass() == o2.getClass()) && streamFieldsAndGetters(o1.getClass()).filter(m -> hasAll(m, Equals.class)).map(m -> equals(m, o1, o2)).filter(b -> !b).findFirst().orElse(true));
    }

    private static int _easyHash(@NotNull Object o) {
        return streamFieldsAndGetters(o.getClass()).filter(f -> hasAll(f, Hash.class)).mapToInt(f -> Objects.hashCode(getFrom(f, o))).reduce(1, (r, h) -> ((31 * r) + h));
    }
}
