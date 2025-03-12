package com.expenseBuddy.controller;


import com.expenseBuddy.exception.UserNotFoundException;
import com.expenseBuddy.exception.UsernameAlreadyExistsException;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //show signup page
    @GetMapping("/signup-view")
    public String showSignupPage() {
        return "signup";
    }

    //Handle user signup
    @PostMapping("/signup-view")
    public String registerUser(@ModelAttribute UserEntity userEntity, Model model) {
        try {
            userService.registerUser(userEntity); //save the user detail
            model.addAttribute("message", "Signup Successful!");
            return "redirect:/auth/signup-success";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    //show signup success page
    @GetMapping("/signup-success")
    public String showSignupSuccessPage() {
        return "signup-success";
    }

    //show login page
    @GetMapping("/login-view")
    public String showLoginPage() {

        return "login";
    }

    //Handle user login
    @PostMapping("/login-view")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String role,
                            Model model) {
        try {
            UserEntity authenticatedUser = userService.authenticateUser(username, password, role);
            if ("ADMIN".equalsIgnoreCase(role)) {
                return "redirect:/admin/dashboard-view"; //Redirect to admin dashboard
            }
            return "redirect:/user/dashboard-view"; //Redirect to user dashboard
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }
}