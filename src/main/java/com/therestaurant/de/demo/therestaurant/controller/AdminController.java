package com.therestaurant.de.demo.therestaurant.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AdminController {
    @GetMapping("/admin")
    public String showHome() {
        return "admin/index";
    }

}
