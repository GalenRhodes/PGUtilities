package com.projectgalen.lib.apple;
// ================================================================================================================================
//     PROJECT: PGFleaMarket
//    FILENAME: Application.java
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
import com.projectgalen.lib.utils.reflect.DefaultInvocationHandler;
import com.projectgalen.lib.utils.reflect.ProxyTools;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.*;
import java.lang.reflect.Proxy;

public final class Application {

    private static final ProxyTools _tools_ = new ProxyTools(WrapEx.get(() -> Class.forName("com.apple.eawt.Application")));

    private final @SuppressWarnings("FieldCanBeLocal") Object _proxy_;
    private final                                      Object _instance_;

    private Application() {
        this._instance_ = _tools_.execStatic("getApplication", null);
        this._proxy_    = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class<?>[] { _tools_._cls_ }, new DefaultInvocationHandler<>(this, Application.class));
    }

    public void addAppEventListener(SystemEventListener listener) {
        _tools_.exec("addAppEventListener", new Class<?>[] { SystemEventListener.class }, _instance_, listener);
    }

    public void disableSuddenTermination() {
        _tools_.exec("disableSuddenTermination", null, _instance_);
    }

    public void enableSuddenTermination() {
        _tools_.exec("enableSuddenTermination", null, _instance_);
    }

    public Image getDockIconImage() {
        return _tools_.exec(Image.class, "getDockIconImage", null, _instance_);
    }

    public PopupMenu getDockMenu() {
        return _tools_.exec(PopupMenu.class, "getDockMenu", null, _instance_);
    }

    public void openHelpViewer() {
        _tools_.exec("openHelpViewer", null, _instance_);
    }

    public void removeAppEventListener(SystemEventListener listener) {
        _tools_.exec("removeAppEventListener", new Class<?>[] { SystemEventListener.class }, _instance_, listener);
    }

    public void requestForeground(boolean allWindows) {
        _tools_.exec("requestForeground", new Class<?>[] { boolean.class }, _instance_, allWindows);
    }

    public void requestToggleFullScreen(Window window) {
        _tools_.exec("requestToggleFullScreen", new Class<?>[] { Window.class }, _instance_, window);
    }

    public void requestUserAttention(boolean critical) {
        _tools_.exec("requestUserAttention", new Class<?>[] { boolean.class }, _instance_, critical);
    }

    public void setAboutHandler(AboutHandler aboutHandler) {
        _tools_.exec("setAboutHandler", new Class<?>[] { AboutHandler.class }, _instance_, aboutHandler);
    }

    public void setDefaultMenuBar(JMenuBar menuBar) {
        _tools_.exec("setDefaultMenuBar", new Class<?>[] { JMenuBar.class }, _instance_, menuBar);
    }

    public void setDockIconBadge(String badge) {
        _tools_.exec("setDockIconBadge", new Class<?>[] { String.class }, _instance_, badge);
    }

    public void setDockIconImage(Image image) {
        _tools_.exec("setDockIconImage", new Class<?>[] { Image.class }, _instance_, image);
    }

    public void setDockIconProgress(int value) {
        _tools_.exec("setDockIconProgress", new Class<?>[] { int.class }, _instance_, value);
    }

    public void setDockMenu(PopupMenu popupMenu) {
        _tools_.exec("setDockMenu", new Class<?>[] { PopupMenu.class }, _instance_, popupMenu);
    }

    public void setOpenFileHandler(OpenFilesHandler openFilesHandler) {
        _tools_.exec("setOpenFileHandler", new Class<?>[] { OpenFilesHandler.class }, _instance_, openFilesHandler);
    }

    public void setOpenURIHandler(OpenURIHandler openURIHandler) {
        _tools_.exec("setOpenURIHandler", new Class<?>[] { OpenURIHandler.class }, _instance_, openURIHandler);
    }

    public void setPreferencesHandler(PreferencesHandler preferencesHandler) {
        _tools_.exec("setPreferencesHandler", new Class<?>[] { PreferencesHandler.class }, _instance_, preferencesHandler);
    }

    public void setPrintFileHandler(PrintFilesHandler printFilesHandler) {
        _tools_.exec("setPrintFileHandler", new Class<?>[] { PrintFilesHandler.class }, _instance_, printFilesHandler);
    }

    public void setQuitHandler(QuitHandler quitHandler) {
        _tools_.exec("setQuitHandler", new Class<?>[] { QuitHandler.class }, _instance_, quitHandler);
    }

    public void setQuitStrategy(QuitStrategy quitStrategy) {
        _tools_.exec("setQuitStrategy", new Class<?>[] { QuitStrategy.class }, _instance_, quitStrategy);
    }

    @NotNull Object getProxy() {
        return _proxy_;
    }

    public static @NotNull Application getApplication() {
        return _Application_.INSTANCE;
    }

    private static final class _Application_ {
        private static final Application INSTANCE = new Application();
    }
}
