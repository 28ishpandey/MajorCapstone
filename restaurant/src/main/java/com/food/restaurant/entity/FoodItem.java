package com.food.restaurant.entity;

import jakarta.persistence.*;

@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodItemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean availability = true;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public FoodItem() {}

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
