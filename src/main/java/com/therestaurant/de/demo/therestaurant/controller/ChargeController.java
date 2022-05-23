package com.therestaurant.de.demo.therestaurant.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.therestaurant.de.demo.therestaurant.dto.ChargeRequest;
import com.therestaurant.de.demo.therestaurant.entity.*;
import com.therestaurant.de.demo.therestaurant.repo.CartRepository;
import com.therestaurant.de.demo.therestaurant.repo.OrderItemRepository;
import com.therestaurant.de.demo.therestaurant.repo.OrderRepository;
import com.therestaurant.de.demo.therestaurant.service.StripeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, String address, Model model) throws StripeException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Cart> carts = cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        float totalAmount = 0;

        for (Cart cart : carts) {
            totalAmount += cart.getQty() * cart.getMenu().getPrice();
        }

        if(totalAmount == 0) {
            return  "redirect:/";
        }

        chargeRequest.setDescription(address);
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        Order order = orderRepository.save(new Order(user.getFirstname() + " " + user.getLastname(), totalAmount, address, charge.getId(), charge.getStatus(), charge.getStatus().toLowerCase().equals("succeeded") ? Order.OrderStatus.Processing : Order.OrderStatus.Canceled));

        for (Cart cart : carts) {
            Menu menu = cart.getMenu();
            orderItemRepository.save(new OrderItem(order, menu.getName(), menu.getPrice(), cart.getQty(), menu.getPrice() * cart.getQty()));
            cartRepository.delete(cart);
        }

        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}