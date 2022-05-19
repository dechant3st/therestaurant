package com.therestaurant.de.demo.threstaurant.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {
    @GetMapping("/admin")
    public String showCategory() {
        return "admin/index";
    }
}
