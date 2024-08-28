package com.food.user.controller;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;

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
        UserResponseDTO responseDTO = userService.createUser(userCreateDTO);
        log.info("User registered successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        log.info("Fetching user with ID: {}", userId);
        UserResponseDTO responseDTO = userService.getUserById(userId);
        log.info("User fetched successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Fetching all users");
        List<UserResponseDTO> responseDTOs = userService.getAllUsers();
        log.info("All users fetched successfully");
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Updating user with ID: {}", userId);
        UserResponseDTO responseDTO = userService.updateUser(userId, userCreateDTO);
        log.info("User updated successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        log.info("User deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        log.info("Login attempt with email: {}", loginDTO.getEmail());
        UserResponseDTO responseDTO = userService.login(loginDTO);
        log.info("Login successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        log.info("Forgot password request received for email: {}", forgotPasswordDTO.getEmail());
        userService.forgotPassword(forgotPasswordDTO);
        log.info("Forgot password email sent");
        return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);

    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<UserResponseDTO> changePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        log.info("Password change request for user ID: {}", userId);
        UserResponseDTO responseDTO = userService.changePassword(userId, newPassword);
        log.info("Password changed successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}