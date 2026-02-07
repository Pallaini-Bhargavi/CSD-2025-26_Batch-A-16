package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class EncodeController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public EncodeController(UserRepository userRepository,
                            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //  Only logged-in users can access encode page
    @GetMapping("/encode")
    public String encodePage(HttpSession session) {
        if (session.getAttribute("USER_EMAIL") == null) {
            return "redirect:/login";
        }
        return "encode";
    }

    //  Validate email + password before encoding
    @PostMapping("/encode/validate")
    @ResponseBody
    public ResponseEntity<String> validateEncodeUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        if (session.getAttribute("USER_EMAIL") == null) {
            return ResponseEntity.status(401)
                    .body("Session expired. Please login again.");
        }

        User user = userRepository.findByUserEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body("Email not found.");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return ResponseEntity.badRequest()
                    .body("Incorrect password.");
        }

        return ResponseEntity.ok("VALID");
    }
}
