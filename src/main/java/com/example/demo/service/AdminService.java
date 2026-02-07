package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(String email, String password) {

        Optional<Admin> adminOpt = adminRepo.findByEmail(email);

        if (adminOpt.isEmpty()) {
            return false;
        }

        Admin admin = adminOpt.get();
        return passwordEncoder.matches(password, admin.getPasswordHash());
    }
}
