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

import com.formdev.flatlaf.util.SystemInfo;
import com.projectgalen.lib.utils.Obj;
import com.projectgalen.lib.utils.reflect.MethodInfo;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.*;

@SuppressWarnings("unused")
public final class Application {

    private static final Class<?> CLS = (SystemInfo.isMacOS ? Obj.classForname("com.apple.eawt.Application") : Application.class);

    private final @NotNull Object instance;

    private Application() {
        this.instance = (SystemInfo.isMacOS ? new MethodInfo(CLS, "getApplication", true).invoke(null) : this);
    }

    public void addAppEventListener(SystemEventListener systemEventListener) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "addAppEventListener", false, SystemEventListener.class).invoke(instance, systemEventListener);
    }

    public void disableSuddenTermination() {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "disableSuddenTermination", false).invoke(instance);
    }

    public void enableSuddenTermination() {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "enableSuddenTermination", false).invoke(instance);
    }

    public Image getDockIconImage() {
        return (SystemInfo.isMacOS ? new MethodInfo(CLS, "getDockIconImage", false).invoke(instance) : null);
    }

    public PopupMenu getDockMenu() {
        return (SystemInfo.isMacOS ? new MethodInfo(CLS, "getDockMenu", false).invoke(instance) : null);
    }

    public void openHelpViewer() {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "openHelpViewer", false).invoke(instance);
    }

    public void removeAppEventListener(SystemEventListener systemEventListener) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "removeAppEventListener", false, SystemEventListener.class).invoke(instance, systemEventListener);
    }

    public void requestForeground(boolean value) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "requestForeground", false, boolean.class).invoke(instance, value);
    }

    public void requestToggleFullScreen(@NotNull Window window) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "requestToggleFullScreen", false, Window.class).invoke(instance, window);
    }

    public void requestUserAttention(boolean requiresUserAttention) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "requestUserAttention", false, boolean.class).invoke(instance, requiresUserAttention);
    }

    public void setAboutHandler(AboutHandler aboutHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setAboutHandler", false, AboutHandler.class).invoke(instance, aboutHandler);
    }

    public void setDefaultMenuBar(JMenuBar menuBar) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setDefaultMenuBar", false, JMenuBar.class).invoke(instance, menuBar);
    }

    public void setDockIconBadge(String badge) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setDockIconBadge", false, String.class).invoke(instance, badge);
    }

    public void setDockIconImage(Image image) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setDockIconImage", false, Image.class).invoke(instance, image);
    }

    public void setDockIconProgress(int value) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setDockIconProgress", false, int.class).invoke(instance, value);
    }

    public void setDockMenu(PopupMenu popupMenu) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setDockMenu", false, PopupMenu.class).invoke(instance, popupMenu);
    }

    public void setOpenFileHandler(OpenFilesHandler openFilesHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setOpenFileHandler", false, OpenFilesHandler.class).invoke(instance, openFilesHandler);
    }

    public void setOpenURIHandler(OpenURIHandler openURIHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setOpenURIHandler", false, OpenURIHandler.class).invoke(instance, openURIHandler);
    }

    public void setPreferencesHandler(PreferencesHandler preferencesHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setPreferencesHandler", false, PreferencesHandler.class).invoke(instance, preferencesHandler);
    }

    public void setPrintFileHandler(PrintFilesHandler printFilesHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setPrintFileHandler", false, PrintFilesHandler.class).invoke(instance, printFilesHandler);
    }

    public void setQuitHandler(QuitHandler quitHandler) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setQuitHandler", false, QuitHandler.class).invoke(instance, quitHandler);
    }

    public void setQuitStrategy(QuitStrategy quitStrategy) {
        if(SystemInfo.isMacOS) new MethodInfo(CLS, "setQuitStrategy", false, QuitStrategy.class).invoke(instance, quitStrategy);
    }

    public static Application getApplication() {
        return ApplicationHolder.INSTANCE;
    }

    private static final class ApplicationHolder {
        private static final Application INSTANCE = new Application();
    }
}
