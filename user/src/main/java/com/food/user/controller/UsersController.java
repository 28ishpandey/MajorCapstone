package com.food.user.controller;

import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.service.UsersService;
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
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserCreateDTO userCreateDTO) {
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
}