package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.service.CustomerSevice;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/customers")
public class CustomerController {
    @Autowired
    private CustomerSevice customerSevice;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> all()
    {
        return customerSevice.all();
    }
}
