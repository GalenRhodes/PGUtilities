package com.projectgalen.lib.utils.reflect;
// ================================================================================================================================
//     PROJECT: PGFleaMarket
//    FILENAME: ProxyTools.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 20, 2024
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
import com.projectgalen.lib.utils.errors.WrapEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class ProxyTools {

    private static final PGResourceBundle                      msgs            = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final Collector<CharSequence, ?, String>    KJOIN           = Collectors.joining(",", "(", ")");
    private static final Class<?>[]                            NO_PARAMS       = new Class<?>[0];
    private static final Function<Exception, RuntimeException> AS_RT_EXCEPTION = e -> new RuntimeException(e.toString(), e);

    private final Map<String, Method> _cache_  = new TreeMap<>();
    private final Map<String, Method> _scache_ = new TreeMap<>();
    public final  Class<?>            _cls_;

    public ProxyTools(Class<?> cls) {
        this._cls_ = cls;
    }

    public ProxyTools(@NotNull String name) {
        this._cls_ = WrapEx.get(() -> Class.forName(name), AS_RT_EXCEPTION);
    }

    public @SuppressWarnings("UnusedReturnValue") @Nullable Object exec(@NotNull String name, Class<?>[] paramTypes, @NotNull Object target, Object... args) {
        return exec(Object.class, name, paramTypes, target, args);
    }

    public <T> @Nullable T exec(@NotNull Class<T> returnType, @NotNull String name, Class<?>[] paramTypes, @NotNull Object target, Object... args) {
        return returnType.cast(exec(getMethod(name, ofNullable(paramTypes).orElse(NO_PARAMS)), target, args));
    }

    public @Nullable Object exec(@NotNull Method method, Object target, Object... args) {
        return exec(Object.class, method, target, args);
    }

    public <T> @Nullable T exec(@NotNull Class<T> returnType, @NotNull Method method, Object target, Object... args) {
        return returnType.cast(WrapEx.get(() -> method.invoke(target, args), AS_RT_EXCEPTION));
    }

    public @Nullable Object execStatic(@NotNull String name, Class<?>[] paramTypes, Object... args) {
        return execStatic(Object.class, name, paramTypes, args);
    }

    public @Nullable <T> T execStatic(@NotNull Class<T> returnType, @NotNull String name, Class<?>[] paramTypes, Object... args) {
        return execStatic(returnType, getStaticMethod(name, paramTypes), args);
    }

    public @Nullable Object execStatic(@NotNull Method method, Object... args) {
        return execStatic(Object.class, method, args);
    }

    public @Nullable <T> T execStatic(@NotNull Class<T> returnType, @NotNull Method method, Object... args) {
        if(Modifier.isStatic(method.getModifiers())) throw new RuntimeException(msgs.getString("msg.err.not_static_method").formatted(method));
        return WrapEx.get(() -> returnType.cast(method.invoke(_cls_, args)), AS_RT_EXCEPTION);
    }

    public @NotNull RuntimeException getException(@NotNull String name) {
        return new RuntimeException(msgs.getString("msg.err.no_such_method").formatted(name), new NoSuchMethodException(name));
    }

    public @NotNull Method getMethod(@NotNull String name, Class<?>... paramTypes) {
        return _cache_.computeIfAbsent(getKey(name, paramTypes), k -> ofNullable(Reflect.getMethod(_cls_, false, name, paramTypes)).orElseThrow(() -> getException(k)));
    }

    public @NotNull Method getStaticMethod(@NotNull String name, Class<?>... paramTypes) {
        return _scache_.computeIfAbsent(getKey(name, paramTypes), k -> ofNullable(Reflect.getMethod(_cls_, true, name, paramTypes)).orElseThrow(() -> getException(k)));
    }

    private static @NotNull String getKey(@NotNull String name, Class<?> @NotNull [] paramTypes) {
        return name + Arrays.stream(paramTypes).map(Class::getName).collect(KJOIN);
    }
}
