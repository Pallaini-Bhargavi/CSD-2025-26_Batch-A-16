package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepository;

@RestController
public class AuthApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/security-question")
    public ResponseEntity<String> getSecurityQuestion(
            @RequestParam String email) {

        return userRepository.findByUserEmail(email)
                .map(user ->
                        ResponseEntity.ok(
                                user.getSecurityQuestion()))
                .orElseGet(() ->
                        ResponseEntity
                                .status(404)
                                .body("User not found"));
    }
}
