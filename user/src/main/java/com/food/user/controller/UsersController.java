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

/**
 * Controller class for managing user-related operations.
 * Provides endpoints for registering, updating, deleting,
 * and retrieving user information, as well as login and password management.
 */
@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService userService;

    /**
     * Registers a new user.
     *
     * @param userCreateDTO The DTO containing user creation details.
     * @return ResponseEntity containing the registered user information.
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Registering user with email: {}", userCreateDTO.getEmail());
        UserResponseDTO responseDTO = userService.createUser(userCreateDTO);
        log.info("User registered successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Fetches a user by ID.
     *
     * @param userId The ID of the user to fetch.
     * @return ResponseEntity containing the user information.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        log.info("Fetching user with ID: {}", userId);
        UserResponseDTO responseDTO = userService.getUserById(userId);
        log.info("User fetched successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Fetches all users.
     *
     * @return ResponseEntity containing a list of all users.
     */
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Fetching all users");
        List<UserResponseDTO> responseDTOs = userService.getAllUsers();
        log.info("All users fetched successfully");
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    /**
     * Updates an existing user.
     *
     * @param userId        The ID of the user to update.
     * @param userCreateDTO The DTO containing updated user details.
     * @return ResponseEntity containing the updated user information.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Updating user with ID: {}", userId);
        UserResponseDTO responseDTO = userService.updateUser(userId, userCreateDTO);
        log.info("User updated successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        log.info("User deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles user login.
     *
     * @param loginDTO The DTO containing login credentials.
     * @return ResponseEntity containing user information upon successful login.
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        log.info("Login attempt with email: {}", loginDTO.getEmail());
        UserResponseDTO responseDTO = userService.login(loginDTO);
        log.info("Login successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Handles forgot password requests.
     *
     * @param forgotPasswordDTO The DTO containing the user's email for password reset.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        log.info("Forgot password request received for email: {}", forgotPasswordDTO.getEmail());
        userService.forgotPassword(forgotPasswordDTO);
        log.info("Forgot password email sent");
        return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
    }

    /**
     * Changes the password of a user.
     *
     * @param userId     The ID of the user whose password is to be changed.
     * @param newPassword The new password.
     * @return ResponseEntity containing updated user information.
     */
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<UserResponseDTO> changePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        log.info("Password change request for user ID: {}", userId);
        UserResponseDTO responseDTO = userService.changePassword(userId, newPassword);
        log.info("Password changed successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}