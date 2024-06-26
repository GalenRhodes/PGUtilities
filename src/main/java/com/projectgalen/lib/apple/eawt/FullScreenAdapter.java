package com.projectgalen.lib.apple.eawt;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FullScreenAdapter.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 24, 2024
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

import com.projectgalen.lib.apple.eawt.event.FullScreenEvent;
import com.projectgalen.lib.utils.errors.WrapEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })
public class FullScreenAdapter implements FullScreenListener {

    private static final @NotNull Class<?>[]          _INTERFACES_   = { FullScreenListener._CLS_ };
    private static final @NotNull ClassLoader         _LOADER_       = FullScreenAdapter.class.getClassLoader();
    private static final @NotNull Map<String, Method> _METHOD_CACHE_ = Collections.synchronizedMap(new TreeMap<>());

    private final @NotNull Object proxy;

    public FullScreenAdapter()                                                               { proxy = Proxy.newProxyInstance(_LOADER_, _INTERFACES_, (__, m, a) -> invoke(m, a)); }

    public final @Override @NotNull Object getProxy()                                        { return proxy; }

    public @Override void windowEnteredFullScreen(@NotNull FullScreenEvent fullScreenEvent)  { }

    public @Override void windowEnteringFullScreen(@NotNull FullScreenEvent fullScreenEvent) { }

    public @Override void windowExitedFullScreen(@NotNull FullScreenEvent fullScreenEvent)   { }

    public @Override void windowExitingFullScreen(@NotNull FullScreenEvent fullScreenEvent)  { }

    private @Nullable Object invoke(@NotNull Method m, Object @NotNull [] a) throws Throwable {
        try {
            return _METHOD_CACHE_.computeIfAbsent(m.toString(), WrapEx.apply(k -> getClass().getMethod(m.getName(), xlateTypes(m)))).invoke(this, xlateArgs(a));
        }
        catch(WrapEx e) {
            throw e.getCause();
        }
    }

    private static Object @NotNull [] xlateArgs(Object @NotNull [] args) {
        Object[] parameters = new Object[args.length];
        Arrays.setAll(parameters, i -> xlateArg(args[i]));
        return parameters;
    }

    private static @Nullable Object xlateArg(Object arg) {
        return FullScreenEvent._CLS_.isInstance(arg) ? new FullScreenEvent((EventObject)arg) : arg;
    }

    private static Class<?> @NotNull [] xlateTypes(@NotNull Method m) {
        return Arrays.stream(m.getParameterTypes()).map(t -> (t == FullScreenEvent._CLS_) ? FullScreenEvent.class : t).toArray(Class[]::new);
    }
}
