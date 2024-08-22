package com.food.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String contactNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double walletBalance = 1000.0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(userId, users.userId) && Objects.equals(email, users.email) && Objects.equals(contactNumber, users.contactNumber) && Objects.equals(password, users.password) && Objects.equals(firstName, users.firstName) && Objects.equals(lastName, users.lastName) && Objects.equals(walletBalance, users.walletBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, contactNumber, password, firstName, lastName, walletBalance);
    }
}
