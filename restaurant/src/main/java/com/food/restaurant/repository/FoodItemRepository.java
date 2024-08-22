package com.food.restaurant.repository;

import com.food.restaurant.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
}
