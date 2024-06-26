package com.projectgalen.lib.utils.reflect.proxy;
// ================================================================================================================================
//     PROJECT: PGFleaMarket
//    FILENAME: DefaultInvocationHandler.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 17, 2024
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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.projectgalen.lib.utils.reflect.Reflect.getMethod;
import static java.util.Optional.ofNullable;

public final class DefaultInvocationHandler implements InvocationHandler {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private final @NotNull Object instance;

    public DefaultInvocationHandler(@NotNull Object instance) {
        this.instance = instance;
    }

    public @NotNull Object getInstance() {
        return instance;
    }

    public @Override @Nullable Object invoke(Object p, @NotNull Method m, Object... a) throws Throwable {

        return ofNullable(getMethod(instance.getClass(), m.getName(), m.getParameterTypes()))
                .orElseThrow(() -> new NoSuchMethodException(msgs.getString("msg.err.no_such_method").formatted(m)))
                .invoke(instance, a);
    }
}
