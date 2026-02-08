package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private int attempts = 0;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "action_taken_at")
    private LocalDateTime actionTakenAt;


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
    @Column
private LocalDateTime lockedUntil;

    public void setNewPasswordHash(String newPasswordHash) {
        this.newPasswordHash = newPasswordHash;
    }

    public int getAttempts() {
    return attempts;
}

    public void setAttempts(int attempts) {
        this.attempts = attempts;
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
public LocalDateTime getLockedUntil() {
    return lockedUntil;
}

public void setLockedUntil(LocalDateTime lockedUntil) {
    this.lockedUntil = lockedUntil;
}

public LocalDateTime getActionTakenAt() {
    return actionTakenAt;
}

public void setActionTakenAt(LocalDateTime actionTakenAt) {
    this.actionTakenAt = actionTakenAt;
}

}
