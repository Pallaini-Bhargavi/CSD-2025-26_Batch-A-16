package com.example.demo.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCryptoUtil {

    private AESCryptoUtil() {}

    public static byte[] encrypt(String plaintext,
                                 byte[] key)
            throws Exception {

        // AES needs 16 bytes key
        SecretKeySpec keySpec =
                new SecretKeySpec(key, 0, 16, "AES");

        Cipher cipher =
                Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(
                plaintext.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText,
                                 byte[] key)
            throws Exception {

        SecretKeySpec keySpec =
                new SecretKeySpec(key, 0, 16, "AES");

        Cipher cipher =
                Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        return new String(
                cipher.doFinal(cipherText),
                "UTF-8");
    }
}
