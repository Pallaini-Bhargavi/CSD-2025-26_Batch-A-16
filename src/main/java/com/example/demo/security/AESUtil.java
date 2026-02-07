package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AESUtil {

    //  Derive AES-256 key
    public static byte[] deriveAESKey(byte[] sharedSecret)
            throws Exception {

        MessageDigest sha256 =
                MessageDigest.getInstance("SHA-256");

        return sha256.digest(sharedSecret);
    }

    //  Encrypt
    public static byte[] encrypt(
            byte[] aesKey,
            String plaintext) throws Exception {

        Cipher cipher =
                Cipher.getInstance("AES/ECB/PKCS5Padding");

        SecretKeySpec keySpec =
                new SecretKeySpec(aesKey, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(
                plaintext.getBytes(StandardCharsets.UTF_8));
    }

    //  Decrypt (THIS FIXES YOUR ERROR)
    public static String decrypt(
            byte[] aesKey,
            byte[] cipherBytes) throws Exception {

        Cipher cipher =
                Cipher.getInstance("AES/ECB/PKCS5Padding");

        SecretKeySpec keySpec =
                new SecretKeySpec(aesKey, "AES");

        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] plain =
                cipher.doFinal(cipherBytes);

        return new String(plain, StandardCharsets.UTF_8);
    }
    

public static boolean matchesPassword(String rawPassword, String hashedPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(rawPassword, hashedPassword);
}
// 🔐 Encrypt raw bytes (for private key)
public static byte[] encryptBytes(
        byte[] aesKey,
        byte[] data) throws Exception {

    Cipher cipher =
            Cipher.getInstance("AES/ECB/PKCS5Padding");

    SecretKeySpec keySpec =
            new SecretKeySpec(aesKey, "AES");

    cipher.init(Cipher.ENCRYPT_MODE, keySpec);

    return cipher.doFinal(data);
}

// 🔐 Decrypt raw bytes (for private key)
public static byte[] decryptBytes(
        byte[] aesKey,
        byte[] cipherBytes) throws Exception {

    Cipher cipher =
            Cipher.getInstance("AES/ECB/PKCS5Padding");

    SecretKeySpec keySpec =
            new SecretKeySpec(aesKey, "AES");

    cipher.init(Cipher.DECRYPT_MODE, keySpec);

    return cipher.doFinal(cipherBytes);
}

}
