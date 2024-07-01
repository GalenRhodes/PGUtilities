package com.projectgalen.lib.utils.reflect.proxy;
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
import com.projectgalen.lib.utils.reflect.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.projectgalen.lib.utils.reflect.Reflect.findMethods;
import static java.util.Optional.ofNullable;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class ProxyTools {

    private static final PGResourceBundle                      msgs            = new PGResourceBundle("com.projectgalen.lib.utils.messages");
    private static final Collector<CharSequence, ?, String>    KJOIN           = Collectors.joining(",", "(", ")");
    private static final Class<?>[]                            NO_PARAMS       = new Class<?>[0];
    private static final Function<Exception, RuntimeException> AS_RT_EXCEPTION = e -> new RuntimeException(e.toString(), e);

    private final Map<String, Method>         _mcache_ = new TreeMap<>();
    private final Map<String, Method>         _scache_ = new TreeMap<>();
    private final Map<String, Constructor<?>> _ccache_ = new TreeMap<>();

    public final Class<?> cls;

    public ProxyTools(Class<?> cls) {
        this.cls = cls;
    }

    public ProxyTools(@NotNull String name) {
        this(WrapEx.get(() -> Class.forName(name), AS_RT_EXCEPTION));
    }

    public @SuppressWarnings("UnusedReturnValue") @Nullable Object exec(@NotNull String name, Class<?>[] paramTypes, @NotNull Object target, Object... args) {
        return exec(getMethod(name, ofNullable(paramTypes).orElse(NO_PARAMS)), target, args);
    }

    public @Nullable Object exec(@NotNull Method method, Object target, Object... args) {
        return WrapEx.get(() -> method.invoke(target, args), AS_RT_EXCEPTION);
    }

    public @Nullable Object execStatic(@NotNull String name, Class<?>[] paramTypes, Object... args) {
        return execStatic(getStaticMethod(name, ofNullable(paramTypes).orElse(NO_PARAMS)), args);
    }

    public @Nullable Object execStatic(@NotNull String name) {
        return execStatic(getStaticMethod(name, NO_PARAMS));
    }

    public @Nullable Object execStatic(@NotNull Method method, Object... args) {
        if(isNotStatic(method)) throw new RuntimeException(msgs.getString("msg.err.not_static_method").formatted(method));
        return WrapEx.get(() -> method.invoke(cls, args), AS_RT_EXCEPTION);
    }

    public @NotNull RuntimeException getException(@NotNull String name) {
        return new RuntimeException(msgs.getString("msg.err.no_such_method").formatted(name), new NoSuchMethodException(name));
    }

    public @NotNull Object getInstance() {
        return Reflect.getInstance(cls);
    }

    public @NotNull Object getInstance(Class<?>[] paramTypes, Object... args) {
        return Reflect.getInstance(cls, paramTypes, args);
    }

    public @NotNull Method getMethod(@NotNull String name, Class<?>... paramTypes) {
        return _mcache_.computeIfAbsent(getKey(name, paramTypes), k -> findMethods(cls, name, paramTypes).filter(this::isNotStatic).findFirst().orElseThrow(() -> getException(k)));
    }

    public @NotNull Method getStaticMethod(@NotNull String name, Class<?>... paramTypes) {
        return _scache_.computeIfAbsent(getKey(name, paramTypes), k -> findMethods(cls, name, paramTypes).filter(this::isStatic).findFirst().orElseThrow(() -> getException(k)));
    }

    private boolean isNotStatic(Method m) {
        return !isStatic(m);
    }

    private boolean isStatic(@NotNull Method m) {
        return Modifier.isStatic(m.getModifiers());
    }

    private static @NotNull String getKey(@NotNull String name, Class<?> @NotNull [] paramTypes) {
        return name + Arrays.stream(paramTypes).map(Class::getName).collect(KJOIN);
    }
}
