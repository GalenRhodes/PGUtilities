package com.projectgalen.lib.apple.eawt.event;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FullScreenEvent.java
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
import com.projectgalen.lib.apple.eawt.Application;
import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.reflect.MethodInfo;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EventObject;

@SuppressWarnings("unused")
public final class FullScreenEvent extends EventObject {

    public static final  Class<?>   _CLS_      = (SystemInfo.isMacOS ? Obj.classForname("com.apple.eawt.event.FullScreenEvent") : FullScreenEvent.class);
    private static final MethodInfo GET_WINDOW = new MethodInfo(_CLS_, "getWindow", false);

    private final Window window;

    public FullScreenEvent(@NotNull EventObject event) {
        super(SystemInfo.isMacOS ? (((EventObject)_CLS_.cast(event)).getSource()) : Application.getApplication());
        this.window = GET_WINDOW.invoke(event);
    }

    public Window getWindow() {
        return window;
    }
}
