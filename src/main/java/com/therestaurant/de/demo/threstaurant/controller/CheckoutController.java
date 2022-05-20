package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.entity.Cart;
import com.therestaurant.de.demo.threstaurant.dto.ChargeRequest;
import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {
    @Value("${stripe.key.public}")
    private String stripePublicKey;

    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/checkout")
    public String checkout(@RequestParam(name = "address") String address, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Cart> carts = cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        float totalAmount = 0;

        for (Cart cart : carts) {
            totalAmount += cart.getQty() * cart.getMenu().getPrice();
        }

        model.addAttribute("address", address);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("amount", Math.round(totalAmount * 100)); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);

        return "checkout";
    }
}
