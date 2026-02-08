package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.entity.ResetPasswordRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.ResetPasswordRequestRepository;
import com.example.demo.repository.UserRepository;


@RestController
@RequestMapping("/api")
public class ResetPasswordApiController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ResetPasswordRequestRepository resetRepo;

    // @Autowired
    // private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {

    String email = dto.getEmail().trim().toLowerCase();

    User user = userRepo.findByUserEmail(email)
            .orElseThrow(() -> 
                new RuntimeException("Email not registered")
            );

    // 🔒 1. HARD BLOCK IF RESET LOCKED
    if (user.getResetLockedUntil() != null &&
        user.getResetLockedUntil().isAfter(LocalDateTime.now())) {

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        return ResponseEntity.badRequest().body(
            "Account locked until " +
            user.getResetLockedUntil().format(formatter)
        );
    }

    // 🚫 2. BLOCK IF PENDING REQUEST EXISTS
    if (resetRepo.existsByEmailAndStatus(email, "PENDING")) {
        return ResponseEntity.badRequest().body(
            "A reset request is already pending."
        );
    }

    // ❌ 3. WRONG SECURITY ANSWER
    if (!passwordEncoder.matches(dto.getAnswer(),
            user.getSecurityAnswerHash())) {

        int attempts = user.getResetAttempts() + 1;
        user.setResetAttempts(attempts);

        // 🔒 LOCK AFTER 3 ATTEMPTS
        if (attempts >= 3) {
            user.setResetLockedUntil(
                LocalDateTime.now().plusHours(48)
            );
        }

        userRepo.save(user);

        return ResponseEntity.badRequest().body(
            "Security answer incorrect. Attempts left: " +
            Math.max(0, 3 - attempts)
        );
    }
    // ✅ 4. SUCCESS → CREATE ADMIN APPROVAL REQUEST
    ResetPasswordRequest req = new ResetPasswordRequest();
    req.setEmail(email);
    req.setNewPasswordHash(
        passwordEncoder.encode(dto.getNewPassword())
    );
    req.setStatus("PENDING");
    req.setCreatedAt(LocalDateTime.now());

    resetRepo.save(req);

    // 🔒 RESET PASSWORD LOCK (USER LEVEL)
if (user.getResetLockedUntil() != null &&
    user.getResetLockedUntil().isAfter(LocalDateTime.now())) {

    return ResponseEntity.badRequest().body(
        "Reset password locked until " +
        user.getResetLockedUntil()
    );
}
    return ResponseEntity.ok("Approval request sent to admin.");

    
    
}


}