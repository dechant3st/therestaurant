package com.therestaurant.de.demo.therestaurant.controller;

import com.therestaurant.de.demo.therestaurant.entity.*;
import com.therestaurant.de.demo.therestaurant.repo.CategoryRepository;
import com.therestaurant.de.demo.therestaurant.repo.MenuRepository;
import com.therestaurant.de.demo.therestaurant.repo.OrderRepository;
import com.therestaurant.de.demo.therestaurant.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AppController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private  final MenuRepository menuRepository;

    @Autowired
    private  final OrderRepository orderRepository;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/login-redirect")
    public String redirectLogin() {
        if(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIsAdmin()) {
            return "redirect:/admin";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return  "register";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid User user, BindingResult result, Model model) {
        user.setRole("CUSTOMER");
        return userService.register(user, result, model);
    }

    @GetMapping("/")
    public String showHome(Model model) {
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getStatus().equals(Category.CategoryStatus.Active))
                .collect(Collectors.toList());
        List<Menu> menus = menuRepository.findAll()
                .stream()
                .filter(menu -> menu.getStatus().equals(Menu.MenuStatus.Available))
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("menus", menus);

        return "home";
    }

    @GetMapping("/my-orders")
    public String showMyOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        return "order";
    }
}
