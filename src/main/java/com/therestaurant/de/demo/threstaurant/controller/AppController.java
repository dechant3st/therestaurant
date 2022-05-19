package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AppController {

    @Autowired
    private final UserService userService;

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
    public String showHome() {
        if(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIsAdmin()) {
            return "redirect:/admin";
        }

        return "home";
    }
}
