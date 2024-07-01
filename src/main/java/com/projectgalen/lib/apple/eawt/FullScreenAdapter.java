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
import com.projectgalen.lib.utils.PGArrays;
import com.projectgalen.lib.utils.reflect.Reflect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.EventObject;
import java.util.function.Function;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })
public class FullScreenAdapter implements FullScreenListener {

    private static final Function<Class<?>, Class<?>> TYPE_MAPPER = t -> ((t == FullScreenEvent._CLS_) ? FullScreenEvent.class : t);
    private static final Function<Object, Object>     ARG_MAPPER  = o -> (FullScreenEvent._CLS_.isInstance(o) ? new FullScreenEvent((EventObject)FullScreenEvent._CLS_.cast(o)) : o);
    private static final Class<?>[]                   PROXIED     = { _CLS_ };

    private final @NotNull Object proxy;

    public FullScreenAdapter() {
        proxy = Proxy.newProxyInstance(getClass().getClassLoader(), PROXIED, (__, m1, args) -> {
            Class<?>[] pt = PGArrays.map(m1.getParameterTypes(), TYPE_MAPPER);
            return Reflect.streamMethods(getClass()).filter(m2 -> test(m1, m2, pt)).findFirst().orElseThrow(NoSuchMethodException::new).invoke(this, PGArrays.map(Object.class, args, ARG_MAPPER));
        });
    }

    public final @Override @NotNull Object getProxy()                                        { return proxy; }

    public @Override void windowEnteredFullScreen(@NotNull FullScreenEvent fullScreenEvent)  { }

    public @Override void windowEnteringFullScreen(@NotNull FullScreenEvent fullScreenEvent) { }

    public @Override void windowExitedFullScreen(@NotNull FullScreenEvent fullScreenEvent)   { }

    public @Override void windowExitingFullScreen(@NotNull FullScreenEvent fullScreenEvent)  { }

    private boolean test(@NotNull Method m1, @NotNull Method m2, Class<?> @NotNull [] pt) {
        return (Reflect.isStatic(m1) == Reflect.isStatic(m2)) && m1.getName().equals(m2.getName()) && Reflect.compareTypes(pt, m2.getParameterTypes());
    }
}
