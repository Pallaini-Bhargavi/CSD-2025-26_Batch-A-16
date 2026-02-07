package com.example.demo.service;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.spec.ECGenParameterSpec;

import org.springframework.stereotype.Service;

import com.example.demo.security.AESUtil;
@Service
public class KeyService {

    public KeyPair generateECCKeyPair() throws Exception {

        KeyPairGenerator keyGen =
                KeyPairGenerator.getInstance("EC");

        keyGen.initialize(
                new ECGenParameterSpec("secp256r1"));

        return keyGen.generateKeyPair();
    }

    // 🔐 Encrypt private key using password
    public byte[] encryptPrivateKey(byte[] privateKey, String password)
            throws Exception {

        byte[] aesKey = deriveKey(password);
        return AESUtil.encryptBytes(aesKey, privateKey);
    }

    // 🔓 Decrypt private key using password
    public byte[] decryptPrivateKey(byte[] encryptedKey, String password)
            throws Exception {

        byte[] aesKey = deriveKey(password);
        return AESUtil.decryptBytes(aesKey, encryptedKey);
    }

    private byte[] deriveKey(String password) throws Exception {
        MessageDigest sha =
                MessageDigest.getInstance("SHA-256");
        return sha.digest(password.getBytes());
    }
}


