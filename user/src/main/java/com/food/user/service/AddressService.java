package com.food.user.service;

import com.food.user.dto.AddressCreateDTO;
import com.food.user.dto.AddressResponseDTO;
import com.food.user.entity.Address;
import com.food.user.exception.AddressAlreadyExistException;
import com.food.user.exception.AddressNotFoundException;
import com.food.user.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressResponseDTO createAddress(AddressCreateDTO addressCreateDTO) {
        log.info("Creating address for user ID: {}", addressCreateDTO.getUserId());
        Optional<Address> existingTitle = addressRepository.findByAddressTitle(addressCreateDTO.getAddressTitle());
        if(existingTitle.isPresent()) {
            Address address = new Address();
            address.setUserId(addressCreateDTO.getUserId());
            address.setStreet(addressCreateDTO.getStreet());
            address.setCity(addressCreateDTO.getCity());
            address.setState(addressCreateDTO.getState());
            address.setCountry(addressCreateDTO.getCountry());
            address.setPostalCode(addressCreateDTO.getPostalCode());

            Address savedAddress = addressRepository.save(address);
            log.info("Address created with ID: {}", savedAddress.getAddressId());
            return convertToResponseDTO(savedAddress);
        }
        throw new AddressAlreadyExistException();
    }

    public AddressResponseDTO getAddressById(Long addressId) {
        log.info("Fetching address with ID: {}", addressId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);
        return convertToResponseDTO(address);
    }

    public List<AddressResponseDTO> getAddressesByUserId(Long userId) {
        log.info("Fetching addresses for user ID: {}", userId);
        return addressRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AddressResponseDTO updateAddress(Long addressId, AddressCreateDTO addressCreateDTO) {
        log.info("Updating address with ID: {}", addressId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);

        address.setStreet(addressCreateDTO.getStreet());
        address.setCity(addressCreateDTO.getCity());
        address.setState(addressCreateDTO.getState());
        address.setPostalCode(addressCreateDTO.getPostalCode());

        Address savedAddress = addressRepository.save(address);
        log.info("Address updated with ID: {}", savedAddress.getAddressId());
        return convertToResponseDTO(savedAddress);
    }

    public void deleteAddress(Long addressId) {
        log.info("Deleting address with ID: {}", addressId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);
        addressRepository.delete(address);
        log.info("Address deleted with ID: {}", addressId);
    }

    private AddressResponseDTO convertToResponseDTO(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setAddressId(address.getAddressId());
        responseDTO.setStreet(address.getStreet());
        responseDTO.setCity(address.getCity());
        responseDTO.setState(address.getState());
        responseDTO.setPostalCode(address.getPostalCode());
        responseDTO.setCountry(address.getCountry());
        responseDTO.setUserId(address.getUserId());
        return responseDTO;
    }
}
