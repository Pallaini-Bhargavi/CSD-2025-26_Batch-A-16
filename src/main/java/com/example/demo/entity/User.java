package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "public_key")
    private String publicKey;

    // ✅ ADD THIS FIELD
    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer_hash")
    private String securityAnswerHash;

    @Column(name = "reset_attempts")
    private Integer resetAttempts = 0;

    @Column(name = "reset_locked_until")
    private LocalDateTime resetLockedUntil;
@Column(name = "encrypted_private_key", length = 2048)
private String encryptedPrivateKey;

    // ===== GETTERS & SETTERS =====

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }
public String getEncryptedPrivateKey() {
    return encryptedPrivateKey;
}

public void setEncryptedPrivateKey(String encryptedPrivateKey) {
    this.encryptedPrivateKey = encryptedPrivateKey;
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

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }

    public Integer getResetAttempts() {
        return resetAttempts;
    }

    public void setResetAttempts(Integer resetAttempts) {
        this.resetAttempts = resetAttempts;
    }

    public LocalDateTime getResetLockedUntil() {
        return resetLockedUntil;
    }

    public void setResetLockedUntil(LocalDateTime resetLockedUntil) {
        this.resetLockedUntil = resetLockedUntil;
    }
}

