package com.food.user.controller;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.error.CustomException;
import com.food.user.service.UsersService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Registering user with email: {}", userCreateDTO.getEmail());
        try {
            UserResponseDTO responseDTO = userService.createUser(userCreateDTO);
            log.info("User registered successfully");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error registering user", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        log.info("Fetching user with ID: {}", userId);
        try {
            UserResponseDTO responseDTO = userService.getUserById(userId);
            log.info("User fetched successfully");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", userId, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Fetching all users");
        try {
            List<UserResponseDTO> responseDTOs = userService.getAllUsers();
            log.info("All users fetched successfully");
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all users", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Updating user with ID: {}", userId);
        try {
            UserResponseDTO responseDTO = userService.updateUser(userId, userCreateDTO);
            log.info("User updated successfully");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating user with ID: {}", userId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with ID: {}", userId);
        try {
            userService.deleteUser(userId);
            log.info("User deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        log.info("Login attempt with email: {}", loginDTO.getEmail());
        try {
            UserResponseDTO responseDTO = userService.login(loginDTO);
            log.info("Login successful");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Login failed", e);
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        log.info("Forgot password request received for email: {}", forgotPasswordDTO.getEmail());
        try {
            userService.forgotPassword(forgotPasswordDTO);
            log.info("Forgot password email sent");
            return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Failed to process forgot password request", e);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<UserResponseDTO> changePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        log.info("Password change request for user ID: {}", userId);
        try {
            UserResponseDTO responseDTO = userService.changePassword(userId, newPassword);
            log.info("Password changed successfully");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Failed to change password", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}