package com.projectgalen.lib.apple;
// ================================================================================================================================
//     PROJECT: PGFleaMarket
//    FILENAME: FullScreenUtilities.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 16, 2024
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

import com.projectgalen.lib.utils.errors.WrapEx;

import java.awt.*;

public final class FullScreenUtilities {

    private static final Class<?> _cls_ = WrapEx.get(() -> Class.forName("com.apple.eawt.FullScreenUtilities"));

    public static  void addFullScreenListenerTo(Window arg0, FullScreenListener arg1) {
        throw new UnsupportedOperationException();
    }

    public static  void removeFullScreenListenerFrom(Window arg0, FullScreenListener arg1) {
        throw new UnsupportedOperationException();
    }

    public static  void setWindowCanFullScreen(Window arg0, boolean arg1) {
        throw new UnsupportedOperationException();
    }
}
