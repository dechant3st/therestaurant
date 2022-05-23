package com.therestaurant.de.demo.therestaurant.controller;

import com.therestaurant.de.demo.therestaurant.entity.Cart;
import com.therestaurant.de.demo.therestaurant.dto.ChargeRequest;
import com.therestaurant.de.demo.therestaurant.entity.Menu;
import com.therestaurant.de.demo.therestaurant.entity.User;
import com.therestaurant.de.demo.therestaurant.repo.CartRepository;
import com.therestaurant.de.demo.therestaurant.repo.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Value("${stripe.key.public}")
    private String stripePublicKey;

    @GetMapping("/carts")
    public String showCarts(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Cart> carts = cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        float totalAmount = 0;

        for (Cart cart : carts) {
            totalAmount += cart.getQty() * cart.getMenu().getPrice();
        }

        model.addAttribute("carts", carts);
        model.addAttribute("totalAmount", totalAmount);
        //model.addAttribute("amount", Math.round(totalAmount * 100)); // in cents
        model.addAttribute("address", user.getAddress());
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);

        return "cart";
    }

    @PostMapping("/api/carts/{id}")
    public ResponseEntity create(@PathVariable("id") Integer id)
    {
        Optional<Menu> menu = menuRepository.findById(id);

        if(menu.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()) && cart.getMenu().getId().equals(menu.get().getId()))
                .findFirst()
                .map(cart -> {
                    cart.setQty(cart.getQty() + 1);
                    return cartRepository.save(cart);
                }).orElseGet(() -> {
                    Cart cart = new Cart(user, menu.get(), 1);
                    return cartRepository.save(cart);
                });

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/carts/{id}")
    public String updateCart(@PathVariable("id") Integer id, Integer value) {
        Optional<Cart> cart = cartRepository.findById(id);

        cart.map(newCart -> {
            newCart.setQty(newCart.getQty() + value);

            if(newCart.getQty() > 0) {
                return cartRepository.save(newCart);
            }

            cartRepository.delete(newCart);

            return null;
        });

        return "redirect:/carts";
    }
}
