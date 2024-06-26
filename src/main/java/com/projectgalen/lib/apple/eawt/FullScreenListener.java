package com.projectgalen.lib.apple.eawt;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: FullScreenListener.java
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
import com.projectgalen.lib.utils.Obj;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface FullScreenListener extends EventListener {

    @NotNull Class<?> _CLS_ = Obj.classForname("com.apple.eawt.FullScreenListener");

    @NotNull Object getProxy();

    void windowEnteredFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowEnteringFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowExitedFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowExitingFullScreen(@NotNull FullScreenEvent fullScreenEvent);
}
