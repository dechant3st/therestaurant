package com.therestaurant.de.demo.threstaurant.entity;

import com.therestaurant.de.demo.threstaurant.composite.CartId;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "carts")
@Data
@IdClass(CartId.class)
public class Cart {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    @NotNull
    private Integer qty;
}
