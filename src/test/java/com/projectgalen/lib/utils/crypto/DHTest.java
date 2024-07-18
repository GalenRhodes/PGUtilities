package com.projectgalen.lib.utils.crypto;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: DHTest.java
//         IDE: IntelliJ IDEA
//      AUTHOR: Galen Rhodes
//        DATE: July 10, 2024
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

import com.projectgalen.lib.crypto.CryptoTools;
import com.projectgalen.lib.utils.text.Str;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.security.PublicKey;

public class DHTest {
    public DHTest() { }

    public int run(String... args) throws Exception {
        IvParameterSpec bobIv             = CryptoTools.generateIv();
        KeyPair         bobKeyPair        = CryptoTools.generateKeyPair();
        KeyPair         aliceKeyPair      = CryptoTools.generateKeyPair();
        PublicKey       bobPublic         = bobKeyPair.getPublic();
        PublicKey       alicePublic       = aliceKeyPair.getPublic();
        SecretKey       bobSharedSecret   = CryptoTools.createSharedSecret(bobKeyPair.getPrivate(), alicePublic);
        SecretKey       aliceSharedSecret = CryptoTools.createSharedSecret(aliceKeyPair.getPrivate(), bobPublic);

        System.out.printf("Bob Public Key: \"%s\"\n".formatted(CryptoTools.getBase64EncodedPublicKey(bobKeyPair)));

        byte[] bobIvBytes = bobIv.getIV();
        String secIv      = CryptoTools.encryptData(bobSharedSecret, bobIvBytes);
        byte[] encIv      = Str.base64Decode(secIv);
        byte[] plnIv      = CryptoTools.decryptBytes(aliceSharedSecret, secIv);
        IvParameterSpec aliceIv = new IvParameterSpec(plnIv);

        byte[] aliceIvBytes = aliceIv.getIV();
        int    aLen         = aliceIvBytes.length;
        int    bLen         = bobIvBytes.length;
        int    eLen         = encIv.length;
        System.out.printf("Alice IV Length: %d\n", aLen);
        System.out.printf("  Bob IV Length: %d\n", bLen);

        if(aLen == bLen) {
            for(int i = 0; i < Math.max(aLen, eLen); ++i) {
                if((i < aLen) && (i < eLen)) {
                    System.out.printf("%4d %4d %4d\n", encIv[i], bobIvBytes[i], aliceIvBytes[i]);
                }
                else if(i < aLen) {
                    System.out.printf("     %4d %4d\n", bobIvBytes[i], aliceIvBytes[i]);
                }
                else {
                    System.out.printf("%4d\n", encIv[i]);
                }
            }
        }

        return 0;
    }

    public static void main(String... args) {
        try {
            System.exit(new DHTest().run(args));
        }
        catch(Throwable t) {
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
