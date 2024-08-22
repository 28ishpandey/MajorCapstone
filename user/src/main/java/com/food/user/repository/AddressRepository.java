package com.food.user.repository;

import com.food.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUserId(Long userId);
}
