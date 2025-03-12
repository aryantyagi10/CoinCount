package com.expenseBuddy.service;

import com.expenseBuddy.dto.PasswordForm;
import com.expenseBuddy.exception.UserNotFoundException;
import com.expenseBuddy.exception.UsernameAlreadyExistsException;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserEntity userEntity) {
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }

        //Prefix role with "ROLE_" if not already present
        if (!userEntity.getRole().startsWith("ROLE_")) {
            userEntity.setRole("ROLE_" + userEntity.getRole().toUpperCase());
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public UserEntity authenticateUser(String username, String password, String role) {
        UserEntity user = userRepository.findByUsernameAndRole(username, role)
                .orElseThrow(() -> new UserNotFoundException("User not found. Please signup first!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password!");
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    public UserEntity getUserById(Long id) {
        System.out.println("find by id: " + userRepository.findById(id));
        return userRepository.findById(id).orElseThrow(() -> new
                RuntimeException("User not found"));
    }

    public UserEntity findByUsername(String username) {
        System.out.println("find by username " + userRepository.findByUsername(username));
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new
                        UserNotFoundException("User not found with username: " + username));
    }

    //For Admin dashboard Features
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<UserEntity> findUserByRoleAndId(String role, Long id) {
        return userRepository.findByRoleAndId(role, id);
    }

   public List<UserEntity> getAllUsersWithRoleUser(){
        return userRepository.findByRole("ROLE_USER");
    }
    //Add a new user
    public void addUser(UserEntity user) {

        //Encode the password if necessary
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Ensure the role starts with "ROLE_"
        if(!user.getRole().startsWith("ROLE_")){
            user.setRole("ROLE_" +
                    user.getRole().toUpperCase());
        }

        userRepository.save(user);

    }

    //Update an existing user
    public void updateUser(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

        //Update only the fields that are provided
        existingUser.setUsername(user.getUsername());

        existingUser.setEmail(user.getEmail());

        existingUser.setRole(user.getRole());

        //Save updated user
        userRepository.save(existingUser);
    }

    //Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

 //For viewing and editing the admin profile functionality of admin dashboard for admin profile
 public boolean changeAdminPassword(String username, PasswordForm passwordForm) {
     UserEntity admin = findByUsername(username);

     // Verify the old password
     if (!passwordEncoder.matches(passwordForm.getOldPassword(), admin.getPassword())) {
         return false; // Old password is incorrect
     }

     // Check if new password matches confirmation
     if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
         return false; // New passwords do not match
     }

     // Update the password
     admin.setPassword(passwordEncoder.encode(passwordForm.getNewPassword()));
     userRepository.save(admin); // Save changes to the database
     return true;
 }

}


