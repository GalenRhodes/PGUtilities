package com.projectgalen.lib.utils.errors;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: WrapEx.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: May 07, 2024
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

import com.projectgalen.lib.utils.functions.RunnableEx;
import com.projectgalen.lib.utils.functions.SupplierEx;
import org.jetbrains.annotations.NotNull;

public class WrapEx extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public WrapEx() {
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of
     * {@code cause}).  This constructor is useful for runtime exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public WrapEx(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public WrapEx(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.  <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public WrapEx(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     *
     * @since 1.7
     */
    public WrapEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static <T> T get(@NotNull SupplierEx<T, Exception> supplier) {
        try { return supplier.get(); } catch(Exception e) { throw new WrapEx(e.getMessage(), e); }
    }

    public static <T> T get(@NotNull SupplierEx<T, Exception> supplier, @NotNull Runnable fin) {
        try { return supplier.get(); } catch(Exception e) { throw new WrapEx(e.getMessage(), e); } finally { fin.run(); }
    }

    public static void run(@NotNull RunnableEx<Exception> runner) {
        try { runner.run(); } catch(Exception e) { throw new WrapEx(e.getMessage(), e); }
    }

    public static void run(@NotNull RunnableEx<Exception> runner, @NotNull Runnable fin) {
        try { runner.run(); } catch(Exception e) { throw new WrapEx(e.getMessage(), e); } finally { fin.run(); }
    }

    public static WrapEx wrap(@NotNull Throwable e) {
        return ((e instanceof WrapEx we) ? we : new WrapEx(e.getMessage(), e));
    }
}
