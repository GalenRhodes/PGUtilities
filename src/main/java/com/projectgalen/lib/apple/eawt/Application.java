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
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.*;

import static com.projectgalen.lib.utils.reflect.Reflect.getMethod;
import static com.projectgalen.lib.utils.reflect.Reflect.invoke;

public final class Application {

    private static final @NotNull Class<?> _CLS_ = Obj.classForname("com.apple.eawt.Application");

    private final @NotNull Object instance;

    private Application() {
        this.instance = invoke(Obj.requireNonNull(getMethod(_CLS_, true, "getApplication"), RuntimeException::new), null);
    }

    public void addAppEventListener(SystemEventListener systemEventListener) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "getApplication", SystemEventListener.class), RuntimeException::new), instance, systemEventListener);
    }

    public void disableSuddenTermination() {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "disableSuddenTermination"), RuntimeException::new), instance);
    }

    public void enableSuddenTermination() {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "enableSuddenTermination"), RuntimeException::new), instance);
    }

    public Image getDockIconImage() {
        return invoke(Obj.requireNonNull(getMethod(_CLS_, true, "getDockIconImage"), RuntimeException::new), instance);
    }

    public PopupMenu getDockMenu() {
        return invoke(Obj.requireNonNull(getMethod(_CLS_, true, "getDockMenu"), RuntimeException::new), instance);
    }

    public void openHelpViewer() {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "openHelpViewer"), RuntimeException::new), instance);
    }

    public void removeAppEventListener(SystemEventListener systemEventListener) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "removeAppEventListener", SystemEventListener.class), RuntimeException::new), instance, systemEventListener);
    }

    public void requestForeground(boolean value) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "requestForeground", boolean.class), RuntimeException::new), instance, value);
    }

    public void requestToggleFullScreen(Window window) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "requestToggleFullScreen", Window.class), RuntimeException::new), instance, window);
    }

    public void requestUserAttention(boolean requiresUserAttention) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "requestUserAttention", boolean.class), RuntimeException::new), instance, requiresUserAttention);
    }

    public void setAboutHandler(AboutHandler aboutHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setAboutHandler", AboutHandler.class), RuntimeException::new), instance, aboutHandler);
    }

    public void setDefaultMenuBar(JMenuBar menuBar) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setDefaultMenuBar", JMenuBar.class), RuntimeException::new), instance, menuBar);
    }

    public void setDockIconBadge(String badge) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setDockIconBadge", String.class), RuntimeException::new), instance, badge);
    }

    public void setDockIconImage(Image image) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setDockIconImage", Image.class), RuntimeException::new), instance, image);
    }

    public void setDockIconProgress(int value) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setDockIconProgress", int.class), RuntimeException::new), instance, value);
    }

    public void setDockMenu(PopupMenu popupMenu) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setDockMenu", PopupMenu.class), RuntimeException::new), instance, popupMenu);
    }

    public void setOpenFileHandler(OpenFilesHandler openFilesHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setOpenFileHandler", OpenFilesHandler.class), RuntimeException::new), instance, openFilesHandler);
    }

    public void setOpenURIHandler(OpenURIHandler openURIHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setOpenURIHandler", OpenURIHandler.class), RuntimeException::new), instance, openURIHandler);
    }

    public void setPreferencesHandler(PreferencesHandler preferencesHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setPreferencesHandler", PreferencesHandler.class), RuntimeException::new), instance, preferencesHandler);
    }

    public void setPrintFileHandler(PrintFilesHandler printFilesHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setPrintFileHandler", PrintFilesHandler.class), RuntimeException::new), instance, printFilesHandler);
    }

    public void setQuitHandler(QuitHandler quitHandler) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setQuitHandler", QuitHandler.class), RuntimeException::new), instance, quitHandler);
    }

    public void setQuitStrategy(QuitStrategy quitStrategy) {
        invoke(Obj.requireNonNull(getMethod(_CLS_, true, "setQuitStrategy", QuitStrategy.class), RuntimeException::new), instance, quitStrategy);
    }

    public static Application getApplication() {
        return ApplicationHolder.INSTANCE;
    }

    private static final class ApplicationHolder {
        private static final Application INSTANCE = new Application();
    }
}
