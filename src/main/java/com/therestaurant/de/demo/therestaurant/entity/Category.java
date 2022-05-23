package com.therestaurant.de.demo.therestaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    private String name;

    @Enumerated
    @NotNull
    private CategoryStatus status;

    public enum CategoryStatus {
        Active,
        Disabled
    }

    public Category(String name) {
        this.name = name;
        this.status = CategoryStatus.Active;
    }
}
