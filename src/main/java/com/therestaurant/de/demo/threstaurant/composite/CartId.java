package com.therestaurant.de.demo.threstaurant.composite;

import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartId implements Serializable
{
    private User user;

    private Menu menu;

    public CartId() {}

    public CartId(User user, Menu menu)
    {
        this.user = user;
        this.menu = menu;
    }
}
