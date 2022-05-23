package com.therestaurant.de.demo.therestaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="menus")
@Data
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 50)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    private String image;

    @Column(nullable = false)
    @NotNull
    private float price = 0;

    @Enumerated
    @NotNull
    private MenuStatus status;

    public Menu(String name, String description, String image, float price, Category category, MenuStatus status) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.status = status;
        this.category = category;
    }

    public enum MenuStatus {
        Available,
        NotAvailable
    }
}
