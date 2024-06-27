package com.projectgalen.lib.crypto;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Hash.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: June 04, 2024
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

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SuppressWarnings("unused")
public final class Hash {

    private Hash() { }

    public static @NotNull String sha3_256Hash(@NotNull String text) {
        return sha3_256Hash(text.toCharArray());
    }

    public static @NotNull String sha3_256Hash(char @NotNull [] chars) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            ByteBuffer    bytes  = StandardCharsets.UTF_8.newEncoder().encode(CharBuffer.wrap(chars));
            digest.update(bytes);
            String str = Base64.getEncoder().encodeToString(digest.digest());
            for(int i = 0; i < bytes.limit(); i++) bytes.put(i, (byte)0);
            return str;
        }
        catch(NoSuchAlgorithmException | CharacterCodingException e) { throw new RuntimeException(e); }
    }
}
