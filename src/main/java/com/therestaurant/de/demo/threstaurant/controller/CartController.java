package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.dto.CartDto;
import com.therestaurant.de.demo.threstaurant.entity.Cart;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/carts")
public class CartController {
    @Autowired
    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public List<Cart> create(@RequestBody CartDto cart)
    {
        return cartService.addToCart(cart);
    }
}
