package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DecodeController {

    @GetMapping("/decode")
    public String decodePage(HttpSession session) {
        if (session.getAttribute("USER_EMAIL") == null) {
            return "redirect:/login";
        }
        return "decode";
    }
}
