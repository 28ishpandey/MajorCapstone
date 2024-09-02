package com.food.user.controller;

import com.food.user.dto.AddressCreateDTO;
import com.food.user.dto.AddressResponseDTO;
import com.food.user.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressCreateDTO addressCreateDTO) {

        log.info("Creating address for user ID: {}", addressCreateDTO.getUserId());
        AddressResponseDTO responseDTO = addressService.createAddress(addressCreateDTO);
        log.info("Address created successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long addressId) {
        log.info("Fetching address with ID: {}", addressId);
        AddressResponseDTO responseDTO = addressService.getAddressById(addressId);
        log.info("Address fetched successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUserId(@PathVariable Long userId) {
        log.info("Fetching addresses for user ID: {}", userId);
        List<AddressResponseDTO> responseDTOs = addressService.getAddressesByUserId(userId);
        log.info("Addresses fetched successfully");
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressCreateDTO addressCreateDTO) {
        log.info("Updating address with ID: {}", addressId);
        AddressResponseDTO responseDTO = addressService.updateAddress(addressId, addressCreateDTO);
        log.info("Address updated successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        log.info("Deleting address with ID: {}", addressId);
        addressService.deleteAddress(addressId);
        log.info("Address deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}