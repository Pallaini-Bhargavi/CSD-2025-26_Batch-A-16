package com.example.demo.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class ECCKeyDerivationService {

    public byte[] deriveAESKey(
            byte[] privateKeyBytes,
            byte[] publicKeyBytes) throws Exception {

        //  rebuild private key
        KeyFactory kf = KeyFactory.getInstance("EC");

        PrivateKey privateKey = kf.generatePrivate(
                new PKCS8EncodedKeySpec(privateKeyBytes));

        PublicKey publicKey = kf.generatePublic(
                new X509EncodedKeySpec(publicKeyBytes));

        //  ECDH agreement
        KeyAgreement ka = KeyAgreement.getInstance("ECDH");
        ka.init(privateKey);
        ka.doPhase(publicKey, true);

        byte[] sharedSecret = ka.generateSecret();

        //  Use first 32 bytes → AES-256
        return new SecretKeySpec(sharedSecret, 0, 32, "AES")
                .getEncoded();
    }
}
