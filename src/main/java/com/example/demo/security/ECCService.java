package com.example.demo.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.KeyAgreement;

import org.springframework.stereotype.Service;

@Service
public class ECCService {
    public byte[] generateSharedSecret(
            byte[] senderPrivateKeyBytes,
            byte[] receiverPublicKeyBytes) throws Exception {

        System.out.println("\n=== CHECKPOINT 2 : ECC ===");

        System.out.println("Sender Private Key (Base64):");
        System.out.println(Base64.getEncoder()
                .encodeToString(senderPrivateKeyBytes));

        System.out.println("Receiver Public Key (Base64):");
        System.out.println(Base64.getEncoder()
                .encodeToString(receiverPublicKeyBytes));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        PrivateKey privateKey = keyFactory.generatePrivate(
                new PKCS8EncodedKeySpec(senderPrivateKeyBytes));

        PublicKey publicKey = keyFactory.generatePublic(
                new X509EncodedKeySpec(receiverPublicKeyBytes));

        KeyAgreement keyAgreement =
                KeyAgreement.getInstance("ECDH");

        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);

        byte[] sharedSecret =
                keyAgreement.generateSecret();

        System.out.println("ECC Shared Secret (Base64):");
        System.out.println(Base64.getEncoder()
                .encodeToString(sharedSecret));

        System.out.println("=== ECC DONE ===\n");

        return sharedSecret;
    }
}
