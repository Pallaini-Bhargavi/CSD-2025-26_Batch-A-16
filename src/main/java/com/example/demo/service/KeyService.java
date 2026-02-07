package com.example.demo.service;
import java.security.SecureRandom;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.spec.ECGenParameterSpec;

import org.springframework.stereotype.Service;



@Service
public class KeyService {

    public KeyPair generateECCKeyPairFromPassword(String password)
            throws Exception {

        MessageDigest sha256 =
                MessageDigest.getInstance("SHA-256");

        byte[] seed = sha256.digest(password.getBytes());

        SecureRandom random =
                SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed);

        KeyPairGenerator keyGen =
                KeyPairGenerator.getInstance("EC");

        keyGen.initialize(
                new ECGenParameterSpec("secp256r1"),
                random);

        return keyGen.generateKeyPair();
    }
}

