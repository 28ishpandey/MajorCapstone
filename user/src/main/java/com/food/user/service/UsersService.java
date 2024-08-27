package com.food.user.service;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.entity.Users;
import com.food.user.error.CustomException;
import com.food.user.repository.UserRepository;
import com.food.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        log.info("Creating user with email: {}", userCreateDTO.getEmail());
        Users user = new Users();
        user.setEmail(userCreateDTO.getEmail());
        user.setContactNumber(userCreateDTO.getContactNumber());
        String encryptedPassword = PasswordUtil.encryptPassword(userCreateDTO.getPassword());
        user.setPassword(encryptedPassword);
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

    public UserResponseDTO login(LoginDTO loginDTO) {
        log.info("Attempting login for email: {}", loginDTO.getEmail());
        Users user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));

        String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

        if (!decryptedPassword.equals(loginDTO.getPassword())) {
            throw new CustomException("Invalid credentials", HttpStatus.UNAUTHORIZED.value());
        }

        log.info("Login successful for user ID: {}", user.getUserId());
        return convertToResponseDTO(user);
    }

    public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        log.info("Processing forgot password for email: {}", forgotPasswordDTO.getEmail());
        Users user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));

        String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

        sendForgotPasswordEmail(user.getEmail(), decryptedPassword);
        log.info("Forgot password email sent successfully");
    }

    private void sendForgotPasswordEmail(String email, String password) {
        log.info("Sending forgot password email to: {}", email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Forgot Password");
        message.setText("Your password is: " + password);

        javaMailSender.send(message);
    }

    public UserResponseDTO changePassword(Long userId, String newPassword) {
        log.info("Changing password for user ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND.value()));

        String encryptedPassword = PasswordUtil.encryptPassword(newPassword);
        user.setPassword(encryptedPassword);

        Users updatedUser = userRepository.save(user);
        log.info("Password changed successfully for user ID: {}", userId);
        return convertToResponseDTO(updatedUser);
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