package com.example.demo.controller;

import java.util.Base64;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.AESUtil;
import com.example.demo.security.ECCService;
import com.example.demo.stego.DecodePipelineService;
import com.example.demo.stego.PngMetadataUtil;

import jakarta.servlet.http.HttpSession;

@RestController
public class DecodeApiController {

    @Autowired
    private ECCService eccService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DecodePipelineService decodePipeline;

    @Autowired
    private PasswordEncoder passwordEncoder;

   @PostMapping("/api/decode/image")
        public ResponseEntity<String> decodeImage(
        @RequestParam("image") MultipartFile image,
        @RequestParam("password") String password,
        HttpSession session) {

    System.out.println("\n==============================");
    System.out.println("DECODE CHECKPOINT STARTED");
    System.out.println("==============================");

    try {
        // ================= CHECKPOINT 1 : SESSION =================
        String email = (String) session.getAttribute("USER_EMAIL");
        String base64PrivateKey =
                (String) session.getAttribute("PRIVATE_KEY");

        System.out.println("Logged-in USER_EMAIL: " + email);

        if (email == null || base64PrivateKey == null) {
            System.out.println("❌ SESSION EXPIRED");
            return ResponseEntity.status(401).body("Session Expired. Please login again.");
        }

        // ================= CHECKPOINT 2 : PASSWORD =================
        var user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        System.out.println("Entered password: " + password);
        System.out.println("Stored password hash: " + user.getPasswordHash());

        boolean passwordMatch =
                passwordEncoder.matches(password, user.getPasswordHash());

        System.out.println("Password match result: " + passwordMatch);

        if (!passwordMatch) {
            System.out.println("❌ WRONG PASSWORD");
            return ResponseEntity
                    .status(403)
                    .body("Incorrect password. Please try again.");
        }

        System.out.println("✅ PASSWORD CHECK PASSED");

        // ================= CHECKPOINT 3 : METADATA =================
        var reader =
                ImageIO.getImageReadersByFormatName("png").next();

        reader.setInput(
                ImageIO.createImageInputStream(
                        image.getInputStream()));

        IIOMetadata metadata =
                reader.getImageMetadata(0);

        String senderPubBase64 =
                PngMetadataUtil.getSenderPublicKey(metadata);

        int symbolCount =
                PngMetadataUtil.getSymbolCount(metadata);

        System.out.println("Sender public key exists: "
                + (senderPubBase64 != null));
        System.out.println("Symbol count: " + symbolCount);

        if (senderPubBase64 == null) {
            System.out.println("❌ METADATA MISSING");
            return ResponseEntity
                    .badRequest()
                    .body("Invalid Image.Please try again.");
        }

        // ================= CHECKPOINT 4 : ECC =================
        byte[] receiverPrivateKey =
                Base64.getDecoder().decode(base64PrivateKey);

        byte[] senderPublicKey =
                Base64.getDecoder().decode(senderPubBase64);

        byte[] sharedSecret =
                eccService.generateSharedSecret(
                        receiverPrivateKey,
                        senderPublicKey);

        System.out.println("ECC SHARED SECRET (Base64): "
                + Base64.getEncoder().encodeToString(sharedSecret));

        byte[] aesKey =
                AESUtil.deriveAESKey(sharedSecret);

        System.out.println("AES KEY (Base64): "
                + Base64.getEncoder().encodeToString(aesKey));

        // ================= CHECKPOINT 5 : DECODE =================
        System.out.println(">>> DECODE PIPELINE START <<<");

        String plaintext =
                decodePipeline.decode(
                        image, aesKey, symbolCount);


        System.out.println("Recovered text: " + plaintext);
        System.out.println(">>> DECODE PIPELINE END <<<");
        
        if (!plaintext.startsWith("CHS::")) {
    System.out.println("❌ UNAUTHORIZED RECEIVER (INVALID MARKER)");
    return ResponseEntity
            .status(403)
            .body("You are not authorized to decode this message.");
}

// strip marker
plaintext = plaintext.substring(5);


        // ================= SUCCESS =================
        System.out.println("✅ DECODE SUCCESS");
        System.out.println("==============================");
        System.out.println("DECODE CHECKPOINT END");
        System.out.println("==============================\n");

        return ResponseEntity.ok(plaintext);

    } catch (Exception e) {
        System.out.println("Decode failed");
        return ResponseEntity
                .badRequest()
                .body("Decoding failed. Please try again.");
    }
}

}
