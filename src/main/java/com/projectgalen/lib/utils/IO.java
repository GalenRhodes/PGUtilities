package com.projectgalen.lib.utils;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: IO.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: July 18, 2024
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

import com.projectgalen.lib.utils.functions.primitives.consumers.IntConsumerEx;
import com.projectgalen.lib.utils.functions.primitives.suppliers.IntSupplierEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.function.Supplier;

import static com.projectgalen.lib.utils.Util.doInChunks;
import static com.projectgalen.lib.utils.Util.doQuietly;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;

public final class IO {

    private static final PGResourceBundle msgs = new PGResourceBundle("com.projectgalen.lib.utils.messages");

    private IO() { }

    public static @SuppressWarnings("UnusedReturnValue") boolean closeQuietly(@NotNull Closeable closeable) {
        return doQuietly(closeable::close);
    }

    public static @SuppressWarnings("UnusedReturnValue") long copy(@NotNull InputStream src, @NotNull OutputStream dest) throws IOException {
        return copy(src, dest, true);
    }

    public static @SuppressWarnings("UnusedReturnValue") long copy(@NotNull InputStream src, @NotNull OutputStream dest, boolean closeInput) throws IOException {
        byte[] buf = new byte[8192];
        return copy(() -> src.read(buf), i -> dest.write(buf, 0, i), (closeInput ? src : null), dest);
    }

    public static @SuppressWarnings("UnusedReturnValue") long copy(@NotNull Reader src, @NotNull Writer dest) throws IOException {
        return copy(src, dest, true);
    }

    public static @SuppressWarnings("UnusedReturnValue") long copy(@NotNull Reader src, @NotNull Writer dest, boolean closeInput) throws IOException {
        char[] buf = new char[8192];
        return copy(() -> src.read(buf), i -> dest.write(buf, 0, i), (closeInput ? src : null), dest);
    }

    public static @SuppressWarnings("UnusedReturnValue") boolean flushQuietly(@NotNull Flushable flushable) {
        return doQuietly(flushable::flush);
    }

    public static @NotNull BufferedReader getResourceAsReader(@NotNull String resourceName, @NotNull Class<?> referenceClass) throws IOException {
        return getResourceAsReader(resourceName, referenceClass, UTF_8);
    }

    public static @NotNull BufferedReader getResourceAsReader(@NotNull String resourceName, @NotNull Class<?> referenceClass, @NotNull Charset cs) throws IOException {
        return new BufferedReader(new InputStreamReader(Obj.requireNonNull(referenceClass.getResourceAsStream(resourceName), getErr(resourceName)), cs));
    }

    public static @NotNull String readString(@NotNull InputStream inputStream) throws IOException {
        return readString(inputStream, UTF_8);
    }

    public static @NotNull String readString(@NotNull InputStream inputStream, @NotNull Charset cs) throws IOException {
        return readString(new InputStreamReader(inputStream, cs));
    }

    public static @NotNull String readString(@NotNull Reader r) throws IOException {
        return readString(new StringBuilder(), r).toString();
    }

    public static @NotNull StringBuilder readString(@NotNull StringBuilder sb, @NotNull Reader reader) throws IOException {
        char[] buffer = new char[8192];
        copy(() -> reader.read(buffer), i -> sb.append(buffer, 0, i), reader, null);
        return sb;
    }

    private static long copy(@NotNull IntSupplierEx<IOException> getter, @NotNull IntConsumerEx<IOException> putter, @Nullable Closeable cl, @Nullable Flushable fl) throws IOException {
        try {
            return doInChunks(getter, putter);
        }
        finally {
            ofNullable(fl).ifPresent(IO::flushQuietly);
            ofNullable(cl).ifPresent(IO::closeQuietly);
        }
    }

    private static @NotNull Supplier<IOException> getErr(@NotNull String resourceName) {
        return () -> new IOException(msgs.getString("msg.err.missing_resource").formatted(resourceName));
    }
}
