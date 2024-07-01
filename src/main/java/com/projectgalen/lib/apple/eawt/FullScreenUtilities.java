package com.projectgalen.lib.apple.eawt;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FullScreenUtilities.java
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

import com.formdev.flatlaf.util.SystemInfo;
import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.reflect.MethodInfo;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("unused")
public final class FullScreenUtilities {

    private static final Class<?>   _CLS_                         = (SystemInfo.isMacOS ? Obj.classForname("com.apple.eawt.FullScreenUtilities") : FullScreenUtilities.class);
    private static final MethodInfo _addFullScreenListenerTo      = new MethodInfo(_CLS_, "addFullScreenListenerTo", true, Window.class, FullScreenListener._CLS_);
    private static final MethodInfo _removeFullScreenListenerFrom = new MethodInfo(_CLS_, "removeFullScreenListenerFrom", true, Window.class, FullScreenListener._CLS_);
    private static final MethodInfo _setWindowCanFullScreen       = new MethodInfo(_CLS_, "setWindowCanFullScreen", true, Window.class, boolean.class);

    private FullScreenUtilities() { }

    public static void addFullScreenListenerTo(@NotNull Window window, @NotNull FullScreenListener fullScreenListener) {
        if(SystemInfo.isMacOS) _addFullScreenListenerTo.invoke(null, window, fullScreenListener.getProxy());
    }

    public static void removeFullScreenListenerFrom(@NotNull Window window, @NotNull FullScreenListener fullScreenListener) {
        if(SystemInfo.isMacOS) _removeFullScreenListenerFrom.invoke(null, window, fullScreenListener.getProxy());
    }

    public static void setWindowCanFullScreen(@NotNull Window window, boolean flag) {
        if(SystemInfo.isMacOS) _setWindowCanFullScreen.invoke(null, window, flag);
    }
}
