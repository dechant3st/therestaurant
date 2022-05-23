package com.therestaurant.de.demo.therestaurant.service;

import com.therestaurant.de.demo.therestaurant.entity.User;
import com.therestaurant.de.demo.therestaurant.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerSevice {
    @Autowired
    private final UserRepository userRepository;

    public List<User> all()
    {
        return  userRepository.findAllByRole("CUSTOMER");
    }
}
