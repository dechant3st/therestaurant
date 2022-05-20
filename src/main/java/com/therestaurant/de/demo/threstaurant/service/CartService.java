package com.therestaurant.de.demo.threstaurant.service;

import com.therestaurant.de.demo.threstaurant.dto.CartDto;
import com.therestaurant.de.demo.threstaurant.entity.Cart;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.repo.CartRepository;
import com.therestaurant.de.demo.threstaurant.repo.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Cart> all() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    public List<Cart> addToCart(CartDto cart) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Menu> menu = menuRepository.findById(cart.getId());

        if(menu.isPresent()) {
            cartRepository.save(new Cart(user, menu.get(), cart.getQty()));
        }

        return all();
    }
}
