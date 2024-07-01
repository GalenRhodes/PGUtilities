package com.projectgalen.lib.apple.eawt;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Application.java
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

import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.reflect.MethodInfo;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.*;

@SuppressWarnings("unused")
public final class Application {

    private final @NotNull Object instance;

    private Application()                                                       { this.instance = MethodHolder.METHODS[0].invoke(null); }

    public void addAppEventListener(SystemEventListener systemEventListener)    { MethodHolder.METHODS[1].invoke(instance, systemEventListener); }

    public void disableSuddenTermination()                                      { MethodHolder.METHODS[2].invoke(instance); }

    public void enableSuddenTermination()                                       { MethodHolder.METHODS[3].invoke(instance); }

    public Image getDockIconImage()                                             { return MethodHolder.METHODS[4].invoke(instance, new Object[] {}); }

    public PopupMenu getDockMenu()                                              { return MethodHolder.METHODS[5].invoke(instance, new Object[] {}); }

    public void openHelpViewer()                                                { MethodHolder.METHODS[6].invoke(instance); }

    public void removeAppEventListener(SystemEventListener systemEventListener) { MethodHolder.METHODS[7].invoke(instance, systemEventListener); }

    public void requestForeground(boolean value)                                { MethodHolder.METHODS[8].invoke(instance, value); }

    public void requestToggleFullScreen(Window window)                          { MethodHolder.METHODS[9].invoke(instance, window); }

    public void requestUserAttention(boolean requiresUserAttention)             { MethodHolder.METHODS[10].invoke(instance, requiresUserAttention); }

    public void setAboutHandler(AboutHandler aboutHandler)                      { MethodHolder.METHODS[11].invoke(instance, aboutHandler); }

    public void setDefaultMenuBar(JMenuBar menuBar)                             { MethodHolder.METHODS[12].invoke(instance, menuBar); }

    public void setDockIconBadge(String badge)                                  { MethodHolder.METHODS[13].invoke(instance, badge); }

    public void setDockIconImage(Image image)                                   { MethodHolder.METHODS[14].invoke(instance, image); }

    public void setDockIconProgress(int value)                                  { MethodHolder.METHODS[15].invoke(instance, value); }

    public void setDockMenu(PopupMenu popupMenu)                                { MethodHolder.METHODS[16].invoke(instance, popupMenu); }

    public void setOpenFileHandler(OpenFilesHandler openFilesHandler)           { MethodHolder.METHODS[17].invoke(instance, openFilesHandler); }

    public void setOpenURIHandler(OpenURIHandler openURIHandler)                { MethodHolder.METHODS[18].invoke(instance, openURIHandler); }

    public void setPreferencesHandler(PreferencesHandler preferencesHandler)    { MethodHolder.METHODS[19].invoke(instance, preferencesHandler); }

    public void setPrintFileHandler(PrintFilesHandler printFilesHandler)        { MethodHolder.METHODS[20].invoke(instance, printFilesHandler); }

    public void setQuitHandler(QuitHandler quitHandler)                         { MethodHolder.METHODS[21].invoke(instance, quitHandler); }

    public void setQuitStrategy(QuitStrategy quitStrategy)                      { MethodHolder.METHODS[22].invoke(instance, quitStrategy); }

    public static Application getApplication()                                  { return ApplicationHolder.INSTANCE; }

    static final class ApplicationHolder {
        private static final Application INSTANCE = new Application();
    }

    static final class MethodHolder {
        private static final          Class<?>     CLS     = Obj.classForname("com.apple.eawt.Application");
        private static final @NotNull MethodInfo[] METHODS = { new MethodInfo(CLS, "getApplication", true),
                                                               new MethodInfo(CLS, "addAppEventListener", false, SystemEventListener.class),
                                                               new MethodInfo(CLS, "disableSuddenTermination", false),
                                                               new MethodInfo(CLS, "enableSuddenTermination", false),
                                                               new MethodInfo(CLS, "getDockIconImage", false),
                                                               new MethodInfo(CLS, "getDockMenu", false),
                                                               new MethodInfo(CLS, "openHelpViewer", false),
                                                               new MethodInfo(CLS, "removeAppEventListener", false, SystemEventListener.class),
                                                               new MethodInfo(CLS, "requestForeground", false, boolean.class),
                                                               new MethodInfo(CLS, "requestToggleFullScreen", false, Window.class),
                                                               new MethodInfo(CLS, "requestUserAttention", false, boolean.class),
                                                               new MethodInfo(CLS, "setAboutHandler", false, AboutHandler.class),
                                                               new MethodInfo(CLS, "setDefaultMenuBar", false, JMenuBar.class),
                                                               new MethodInfo(CLS, "setDockIconBadge", false, String.class),
                                                               new MethodInfo(CLS, "setDockIconImage", false, Image.class),
                                                               new MethodInfo(CLS, "setDockIconProgress", false, int.class),
                                                               new MethodInfo(CLS, "setDockMenu", false, PopupMenu.class),
                                                               new MethodInfo(CLS, "setOpenFileHandler", false, OpenFilesHandler.class),
                                                               new MethodInfo(CLS, "setOpenURIHandler", false, OpenURIHandler.class),
                                                               new MethodInfo(CLS, "setPreferencesHandler", false, PreferencesHandler.class),
                                                               new MethodInfo(CLS, "setPrintFileHandler", false, PrintFilesHandler.class),
                                                               new MethodInfo(CLS, "setQuitHandler", false, QuitHandler.class),
                                                               new MethodInfo(CLS, "setQuitStrategy", false, QuitStrategy.class) };
    }
}
