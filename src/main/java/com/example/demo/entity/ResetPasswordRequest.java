package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_requests")
public class ResetPasswordRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String newPasswordHash;

    @Column(nullable = false)
    private String status;  

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    
    private int attempts = 0;

    @Column
    private LocalDateTime lockedUntil;


    /* ===== GETTERS & SETTERS ===== */

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPasswordHash() {
        return newPasswordHash;
    }

    public void setNewPasswordHash(String newPasswordHash) {
        this.newPasswordHash = newPasswordHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public int getAttempts() {
    return attempts;
}

public void setAttempts(int attempts) {
    this.attempts = attempts;
}

public LocalDateTime getLockedUntil() {
    return lockedUntil;
}

public void setLockedUntil(LocalDateTime lockedUntil) {
    this.lockedUntil = lockedUntil;
}

}
