package com.example.demo.controller;

import java.security.KeyPair;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KeyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyService keyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
public String register(HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {

    try {
        String email = request.getParameter("email").trim().toLowerCase();
        String password = request.getParameter("password");
        String securityQuestion = request.getParameter("securityQuestion");
        String securityAnswer = request.getParameter("securityAnswer");

        if (userRepository.findByUserEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("alreadyRegistered", true);
            return "redirect:/register";
        }

        // 🔐 Generate ECC key ONCE
        KeyPair keyPair = keyService.generateECCKeyPair();

        User user = new User();
        user.setUserEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setSecurityQuestion(securityQuestion);
        user.setSecurityAnswerHash(
                passwordEncoder.encode(securityAnswer.trim().toLowerCase()));

        // ✅ STORE KEYS DIRECTLY
        user.setPublicKey(Base64.getEncoder()
                .encodeToString(keyPair.getPublic().getEncoded()));

        user.setEncryptedPrivateKey(Base64.getEncoder()
                .encodeToString(keyPair.getPrivate().getEncoded()));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "registerSuccess", "Registration successful.");
        return "redirect:/login";

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute(
                "registerError", "Registration failed");
        return "redirect:/register";
    }
}


    // ================= LOGIN =================

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
public String login(@RequestParam String email,
                    @RequestParam String password,
                    RedirectAttributes redirectAttributes,
                    HttpSession session) {

    User user = userRepository
            .findByUserEmail(email.trim().toLowerCase())
            .orElse(null);

    if (user == null) {
        redirectAttributes.addFlashAttribute(
                "loginError", "Email not found.");
        return "redirect:/login";
    }

    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
        redirectAttributes.addFlashAttribute(
                "loginError", "Incorrect password.");
        return "redirect:/login";
    }

    // ✅ LOAD STORED PRIVATE KEY
    session.setAttribute("USER_EMAIL", user.getUserEmail());
    session.setAttribute("PRIVATE_KEY", user.getEncryptedPrivateKey());

    return "redirect:/home"; // or /encode
}


    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

