package com.expenseBuddy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String redirectToDashboard(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/user/dashboard")
    public String showUserDashboard() {
        return "user-dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard";
    }
}