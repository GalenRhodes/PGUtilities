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

import com.formdev.flatlaf.util.SystemInfo;
import com.projectgalen.lib.apple.eawt.event.FullScreenEvent;
import com.projectgalen.lib.utils.Obj;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * A proxy for the class {@code com.apple.eawt.FullScreenListener}.
 *
 * <p><b>NOTE:</b> Do not implement your own listener by implementing this class.  It lacks the code to be a true proxy for {@code com.apple.eawt.FullScreenListener}. Instead, extend
 * {@link com.projectgalen.lib.apple.eawt.FullScreenAdapter} and override the needed methods.</p>
 *
 * <p>When running on Windows or Linux this class does nothing.</p>
 *
 * <p>In order to use this class, you must launch the Java application with the following JVM command-line parameters:</p>
 * <blockquote><pre>--add-opens java.desktop/com.apple.eawt=ALL-UNNAMED --add-opens java.desktop/com.apple.eawt.event=ALL-UNNAMED</pre></blockquote>
 */
@SuppressWarnings("unused")
public interface FullScreenListener extends EventListener {

    @NotNull Class<?> _CLS_ = (SystemInfo.isMacOS ? Obj.classForname("com.apple.eawt.FullScreenListener") : FullScreenListener.class);

    @NotNull Object getProxy();

    void windowEnteredFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowEnteringFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowExitedFullScreen(@NotNull FullScreenEvent fullScreenEvent);

    void windowExitingFullScreen(@NotNull FullScreenEvent fullScreenEvent);
}
