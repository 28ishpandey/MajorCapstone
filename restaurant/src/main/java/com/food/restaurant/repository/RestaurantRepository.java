package com.food.restaurant.repository;

import com.food.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
  Optional<Restaurant> findByEmail(String email);
}
