package com.expenseBuddy.controller;

import com.expenseBuddy.dto.PasswordForm;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UserService userService;

    //Constructor-based dependency injection
    private AdminDashboardController(UserService userService) {
        this.userService = userService;
    }


    //For viewing users
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        List<UserEntity> users = userService.getAllUsersWithRoleUser();
        model.addAttribute("users", users);
        return "admin-user-management";
    }



    //Get the user details for editing
    @GetMapping("/users/edit/{id}")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.getUserById(id);
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin-user-edit";
    }

    //Update user details after edit
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute UserEntity updatedUser) {

        //Retrieve the existing user from the database
        UserEntity existingUser = userService.getUserById(id);

        //Update only modifiable fields
        existingUser.setUsername(updatedUser.getUsername());

        existingUser.setEmail(updatedUser.getEmail());

        existingUser.setRole(updatedUser.getRole());

        //Save the updated user
        userService.updateUser(existingUser);
        return "redirect:/admin/users";  //Redirect back to the dashboard
    }

    //For deleting users
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    //for adding a new user
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        //Provide a blank UserEntity for the form
        model.addAttribute("user", new UserEntity());
        return "admin-user-add";
    }

    //Save new user
    @PostMapping("/users/add")
    public String saveNewUser(@ModelAttribute("user") UserEntity user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/dashboard-view")
    public String showAdminDashboard(Model model, Authentication authentication) {

        //Get the logged-in admin's username
        String username = authentication.getName();

        //Add the username to the model
        model.addAttribute("username", username);
        return "admin-dashboard";
    }

    //For viewing and editing the admin profile

    //For viewing and editing the admin profile
    @GetMapping("/profile")
    public String viewAdminProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        UserEntity admin = userService.findByUsername(username);
        model.addAttribute("admin", admin);
        return "admin-profile";
    }


        @GetMapping("/profile/change-password")
        public String changeAdminPassword(Model model) {
            model.addAttribute("passwordForm", new PasswordForm());
            return "change-admin-password"; // Ensure this view exists
        }

        @PostMapping("/profile/change-password")
        public String updateAdminPassword(@ModelAttribute("passwordForm") PasswordForm passwordForm,
                                          Authentication authentication, Model model) {
            String username = authentication.getName(); // Get username of logged-in admin
            boolean isPasswordChanged = userService.changeAdminPassword(username, passwordForm);

            if (!isPasswordChanged) {
                model.addAttribute("error", "Invalid old password or passwords do not match.");
                return "redirect:change-admin-password"; // Stay on the same page with error
            }

            return "redirect:/admin/profile?success-password"; // Redirect to profile on success
        }
}

