package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Optional;

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

    

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {

        String email = dto.getEmail().trim().toLowerCase();

        Optional<User> userOpt =
                userRepo.findByUserEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email not registered.");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(
                dto.getAnswer(),
                user.getSecurityAnswerHash())) {

            return ResponseEntity
                    .badRequest()
                    .body("Security answer incorrect.");
        }

        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setEmail(email);
        req.setNewPasswordHash(
                passwordEncoder.encode(dto.getNewPassword()));
        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());

        resetRepo.save(req);

        return ResponseEntity.ok(
                "Approval request sent to admin.");
    }
}
