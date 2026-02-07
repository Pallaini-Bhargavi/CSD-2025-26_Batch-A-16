package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class EncodeService {
public byte[] encryptAES(byte[] aesKey, String plaintext) throws Exception {

    long aesStart = System.nanoTime();

    SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key);

    byte[] result = cipher.doFinal(
            plaintext.getBytes(StandardCharsets.UTF_8));

    long aesEnd = System.nanoTime();
    System.out.println("AES Encryption Time (ms): " +
            (aesEnd - aesStart) / 1_000_000.0);

    return result;
}


public byte[] deriveAESKey(byte[] sharedSecret) throws Exception {

    long keyStart = System.nanoTime();

    MessageDigest sha = MessageDigest.getInstance("SHA-256");
    byte[] key = sha.digest(sharedSecret);

    long keyEnd = System.nanoTime();
    System.out.println("AES Key Derivation Time (ms): " +
            (keyEnd - keyStart) / 1_000_000.0);

    return key;
}
}
