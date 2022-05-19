package com.therestaurant.de.demo.threstaurant.service;

import com.therestaurant.de.demo.threstaurant.dto.CartDto;
import com.therestaurant.de.demo.threstaurant.entity.Cart;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.repo.CartRepository;
import com.therestaurant.de.demo.threstaurant.repo.MenuRepository;
import com.therestaurant.de.demo.threstaurant.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Cart> all() {
        return cartRepository.findAll();
    }

    public List<Cart> addToCart(CartDto cart) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Menu menu = menuRepository.getById(cart.getId());
        System.out.println(menu);

        Optional<Cart> dbCart = cartRepository.findCartItem(user.getId(), cart.getId());

        if(dbCart.isPresent()) {
            Cart newCart = dbCart.get();
            newCart.setQty(newCart.getQty() + cart.getQty());
            cartRepository.save(newCart);
            return cartRepository.findAllByUserId(user.getId());
        }

        cartRepository.save(new Cart(user, menu, cart.getQty()));
        return cartRepository.findAllByUserId(user.getId());
    }
}
