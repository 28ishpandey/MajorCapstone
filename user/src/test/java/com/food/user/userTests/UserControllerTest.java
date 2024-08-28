package com.food.user.userTests;

import com.food.user.controller.UsersController;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.exception.CustomException;
import com.food.user.service.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("test@example.com");
        userCreateDTO.setContactNumber("1234567890");
        userCreateDTO.setPassword("password");
        userCreateDTO.setFirstName("John");
        userCreateDTO.setLastName("Doe");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(1L);
        responseDTO.setEmail("test@example.com");
        responseDTO.setContactNumber("1234567890");
        responseDTO.setFirstName("John");
        responseDTO.setLastName("Doe");
        responseDTO.setWalletBalance(1000.0);

        Mockito.when(usersService.createUser(Mockito.any(UserCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"contactNumber\":\"1234567890\", \"password\":\"password\", \"firstName\":\"John\", \"lastName\":\"Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("1234567890"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(1L);
        responseDTO.setEmail("test@example.com");
        responseDTO.setContactNumber("1234567890");
        responseDTO.setFirstName("John");
        responseDTO.setLastName("Doe");
        responseDTO.setWalletBalance(1000.0);

        Mockito.when(usersService.getUserById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("1234567890"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        Mockito.when(usersService.getUserById(1L)).thenThrow(new CustomException("User not found", HttpStatus.NOT_FOUND.value()));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        UserResponseDTO responseDTO1 = new UserResponseDTO();
        responseDTO1.setUserId(1L);
        responseDTO1.setEmail("test1@example.com");
        responseDTO1.setContactNumber("1234567890");
        responseDTO1.setFirstName("John");
        responseDTO1.setLastName("Doe");
        responseDTO1.setWalletBalance(1000.0);

        UserResponseDTO responseDTO2 = new UserResponseDTO();
        responseDTO2.setUserId(2L);
        responseDTO2.setEmail("test2@example.com");
        responseDTO2.setContactNumber("0987654321");
        responseDTO2.setFirstName("Jane");
        responseDTO2.setLastName("Doe");
        responseDTO2.setWalletBalance(1000.0);

        List<UserResponseDTO> allUsers = Arrays.asList(responseDTO1, responseDTO2);

        Mockito.when(usersService.getAllUsers()).thenReturn(allUsers);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].userId").value(2L))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("updated@example.com");
        userCreateDTO.setContactNumber("9876543210");
        userCreateDTO.setPassword("newpassword");
        userCreateDTO.setFirstName("Updated");
        userCreateDTO.setLastName("User");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(1L);
        responseDTO.setEmail("updated@example.com");
        responseDTO.setContactNumber("9876543210");
        responseDTO.setFirstName("Updated");
        responseDTO.setLastName("User");
        responseDTO.setWalletBalance(1000.0);

        Mockito.when(usersService.updateUser(Mockito.eq(1L), Mockito.any(UserCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"updated@example.com\", \"contactNumber\":\"9876543210\", \"password\":\"newpassword\", \"firstName\":\"Updated\", \"lastName\":\"User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("9876543210"))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        Mockito.doNothing().when(usersService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        Mockito.doThrow(new CustomException("User not found", HttpStatus.NOT_FOUND.value())).when(usersService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }
}
