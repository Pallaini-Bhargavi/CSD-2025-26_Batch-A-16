package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Column(name = "encrypted_private_key", columnDefinition = "TEXT", nullable = false)
    private String encryptedPrivateKey;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer_hash", nullable = false)
    private String securityAnswerHash;

    @Column(name = "reset_attempts", nullable = false)
    private int resetAttempts = 0;

    @Column(name = "reset_locked_until")
    private LocalDateTime resetLockedUntil;

    @Column(nullable = false)
    private int loginFailAttempts = 0;

    @Column
    private LocalDateTime loginLockedUntil;

public LocalDateTime getResetLockedUntil() {
    return resetLockedUntil;
}

public void setResetLockedUntil(LocalDateTime resetLockedUntil) {
    this.resetLockedUntil = resetLockedUntil;
}

    public int getResetAttempts() {
        return resetAttempts;
    }

    public void setResetAttempts(int resetAttempts) {
        this.resetAttempts = resetAttempts;
    }
public int getLoginFailAttempts() {
    return loginFailAttempts;
}

public void setLoginFailAttempts(int loginFailAttempts) {
    this.loginFailAttempts = loginFailAttempts;
}

public LocalDateTime getLoginLockedUntil() {
    return loginLockedUntil;
}

public void setLoginLockedUntil(LocalDateTime loginLockedUntil) {
    this.loginLockedUntil = loginLockedUntil;
}

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
