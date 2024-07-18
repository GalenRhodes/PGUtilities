package com.projectgalen.lib.crypto;
// ================================================================================================================================
//     PROJECT: PGUtilities
//    FILENAME: Crypto.java
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

import com.projectgalen.lib.utils.PGProperties;
import com.projectgalen.lib.utils.PGResourceBundle;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import static com.projectgalen.lib.crypto.CryptoTools.*;

@SuppressWarnings("unused")
public final class Crypto {
    private static final PGResourceBundle msgs  = new PGResourceBundle("com.projectgalen.lib.crypto.crypto_messages");
    private static final PGProperties     props = PGProperties.getProperties("crypto_settings.properties", Crypto.class);

    private final IvParameterSpec iv;
    private final SecretKey       secretKey;
    private final PublicKeyInfo   publicKeyInfo;
    private final KeyPair         keyPair;

    public Crypto(@NotNull DiffieHellmanHandshakeDelegate delegate) throws Exception {
        keyPair       = generateKeyPair();
        publicKeyInfo = delegate.apply(getBase64EncodedPublicKey(keyPair));
        secretKey     = createSharedSecret(keyPair.getPrivate(), publicKeyInfo.publicKey());
        iv            = new IvParameterSpec(CryptoTools.decryptBytes(secretKey, publicKeyInfo.iv()));
    }

    public @NotNull String decrypt(@NotNull String base64EncodedCipherText) throws GeneralSecurityException {
        return CryptoTools.decryptData(secretKey, iv, base64EncodedCipherText);
    }

    public byte @NotNull [] decryptBytes(@NotNull String base64EncodedCipherText) throws GeneralSecurityException {
        return CryptoTools.decryptBytes(secretKey, iv, base64EncodedCipherText);
    }

    public @NotNull String encrypt(@NotNull String str) throws GeneralSecurityException {
        return CryptoTools.encryptData(secretKey, iv, str);
    }

    public @NotNull String encrypt(byte @NotNull [] data) throws GeneralSecurityException {
        return CryptoTools.encryptData(secretKey, iv, data);
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public PublicKeyInfo getPublicKeyInfo() {
        return publicKeyInfo;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public @Override @NotNull String toString() {
        StringBuilder sb = new StringBuilder();
        String        f1 = props.getProperty("to.str.fmt1");
        String        f2 = props.getProperty("to.str.fmt2");

        sb.append(String.format(f1, msgs.getString("msg.label.provider"), getProviderName())).append("; ");
        sb.append(String.format(f1, msgs.getString("msg.label.public_key_info"), publicKeyInfo)).append("; ");
        sb.append(String.format(f2, msgs.getString("msg.label.iv_length"), props.getInt("crypto.iv.length"))).append("; ");
        sb.append(String.format(f1, msgs.getString("msg.label.aes_algorithm"), props.getProperty("crypto.aes.algorithm"))).append("; ");
        sb.append(String.format(f2, msgs.getString("msg.label.aes_key_length"), props.getInt("crypto.aes.key_length"))).append("; ");
        sb.append(String.format(f1, msgs.getString("msg.label.aes_transform"), props.getProperty("crypto.aes.transformation.with_iv"))).append("; ");
        sb.append(String.format(f1, msgs.getString("msg.label.aes_transform_no_iv"), props.getProperty("crypto.aes.transformation.no_iv"))).append("; ");
        sb.append(String.format(f1, msgs.getString("msg.label.diffie_hellman_algorithm"), getDHAlgorithm())).append("; ");
        sb.append(String.format(f2, msgs.getString("msg.label.diffie_hellman_key_length"), getDefaultDHKeyLength())).append(';');

        return sb.toString();
    }
}
