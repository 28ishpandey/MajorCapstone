package com.food.user.service;

import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.entity.Address;
import com.food.user.entity.Users;
import com.food.user.error.CustomException;
import com.food.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        log.info("Creating user with email: {}", userCreateDTO.getEmail());
        Users user = new Users();
        user.setEmail(userCreateDTO.getEmail());
        user.setContactNumber(userCreateDTO.getContactNumber());
        user.setPassword(userCreateDTO.getPassword());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setWalletBalance(1000.0);

        Users savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getUserId());
        return convertToResponseDTO(savedUser);
    }

    public UserResponseDTO getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));
        return convertToResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long userId, UserCreateDTO userCreateDTO) {
        log.info("Updating user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));

        user.setEmail(userCreateDTO.getEmail());
        user.setContactNumber(userCreateDTO.getContactNumber());
        user.setPassword(userCreateDTO.getPassword());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());

        Users savedUser = userRepository.save(user);
        log.info("User updated with ID: {}", savedUser.getUserId());
        return convertToResponseDTO(savedUser);
    }

    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));
        userRepository.delete(user);
        log.info("User deleted with ID: {}", userId);
    }

    private UserResponseDTO convertToResponseDTO(Users user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setContactNumber(user.getContactNumber());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setWalletBalance(user.getWalletBalance());
        return responseDTO;
    }
}