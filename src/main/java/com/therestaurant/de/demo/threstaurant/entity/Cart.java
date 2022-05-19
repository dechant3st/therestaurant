package com.therestaurant.de.demo.threstaurant.entity;

import com.therestaurant.de.demo.threstaurant.composite.CartId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "carts")
@Data
@IdClass(CartId.class)
@NoArgsConstructor
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

    public Cart(User user, Menu menu, Integer qty) {
        this.user = user;
        this.menu = menu;
        this.qty = qty;
    }
}
