package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import org.springframework.ui.Model;
import com.example.demo.entity.ResetPasswordRequest;

import com.example.demo.repository.ResetPasswordRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.MailService;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ResetPasswordRequestRepository resetRepo;

    @Autowired
    private MailService mailService;


    /* ================= ADMIN LOGIN PAGE ================= */
    @GetMapping("/admin-login")
    public String adminLoginPage() {
        return "admin-login";
    }

    /* ================= ADMIN LOGIN PROCESS ================= */
    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

    if (email == null || email.trim().isEmpty()) {
        redirectAttributes.addFlashAttribute(
                "error", "Admin email is required."
        );
        return "redirect:/admin-login";
    }

    if (password == null || password.trim().isEmpty()) {
        redirectAttributes.addFlashAttribute(
                "error", "Admin password is required."
        );
        return "redirect:/admin-login";
    }

    if (!adminService.login(email, password)) {
        redirectAttributes.addFlashAttribute(
                "error", "Admin details not found."
        );
        return "redirect:/admin-login";
    }

    // SUCCESS
    session.setAttribute("ADMIN", email);

    //  MOVE TO DASHBOARD 
    return "redirect:/admin-dashboard";
}

    /* ================= ADMIN DASHBOARD ================= */
    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {

    if (session.getAttribute("ADMIN") == null) {
        return "redirect:/admin-login";
    }
    model.addAttribute("users", userRepository.findAll());
    model.addAttribute("pendingRequests", resetRepo.findByStatus("PENDING"));
    model.addAttribute("approvedRequests", resetRepo.findByStatus("APPROVED"));
    model.addAttribute("rejectedRequests", resetRepo.findByStatus("REJECTED"));

    return "admin-dashboard";
}
    
@Transactional
@PostMapping("/api/admin/approve/{id}")
public ResponseEntity<?> approve(@PathVariable Long id) {

    ResetPasswordRequest req =
            resetRepo.findById(id).orElseThrow();

    User user =
            userRepository.findByUserEmail(req.getEmail())
                    .orElseThrow();

    // ✅ ONLY update password hash
    user.setPasswordHash(req.getNewPasswordHash());
    userRepository.save(user);

    // ✅ update request status
    req.setStatus("APPROVED");
    resetRepo.save(req);

    try {
        mailService.sendTextMail(
            req.getEmail(),
            "Password Reset Approved",
            "Your password reset request has been approved."
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    return ResponseEntity.ok().build();
}


@PostMapping("/api/admin/reject/{id}")
public ResponseEntity<?> reject(@PathVariable Long id) {

    ResetPasswordRequest req =
            resetRepo.findById(id).orElseThrow();

    if (!"PENDING".equals(req.getStatus())) {
        return ResponseEntity
                .badRequest()
                .body("Request already processed.");
    }

    req.setStatus("REJECTED");
    resetRepo.save(req);

    try {
        mailService.sendTextMail(
            req.getEmail(),
            "Password Reset Rejected",
            "Your password reset request was rejected."
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    return ResponseEntity.ok().build();
}


/* ================= ADMIN LOGOUT ================= */
    @GetMapping("/admin-logout")
    public String adminLogout(HttpSession session,
                              RedirectAttributes redirectAttributes) {

        session.invalidate();

        redirectAttributes.addFlashAttribute(
                "logoutMsg", "Admin logged out successfully."
        );

        return "redirect:/admin-login";
    }
}
