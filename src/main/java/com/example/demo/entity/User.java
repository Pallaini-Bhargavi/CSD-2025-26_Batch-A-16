package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
    private String publicKey;

    // ✅ ENCRYPTED PRIVATE KEY (NOT RAW)
    @Column(name = "encrypted_private_key", columnDefinition = "TEXT", nullable = false)
    private String encryptedPrivateKey;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer_hash", nullable = false)
    private String securityAnswerHash;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    public void setEncryptedPrivateKey(String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }
}
