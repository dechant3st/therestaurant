package com.therestaurant.de.demo.therestaurant.controller;

import com.therestaurant.de.demo.therestaurant.entity.User;
import com.therestaurant.de.demo.therestaurant.exception.UserNotFoundException;
import com.therestaurant.de.demo.therestaurant.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/admin/customers")
    public String showCustomers(Model model) {
        List<User> customers = userRepository.findAll()
                .stream()
                .filter(customer -> !customer.getIsAdmin())
                .collect(Collectors.toList());

        model.addAttribute("customers", customers);

        return "admin/customer";
    }

    @GetMapping("/admin/customers/add")
    public String showAddCustomer(Model model) {
        User customer = new User();
        customer.setRole("CUSTOMER");

        model.addAttribute("customer", customer);
        return "admin/addUpdateCustomer";
    }

    @PostMapping("/admin/customers/add")
    public String addCustomer(@Valid @ModelAttribute("customer") User customer, BindingResult result, Model model) {
        boolean exist = userRepository.findByUsername(customer.getUsername()).isPresent();
        if(exist) {
            FieldError nameTaken = new FieldError("user", "username", "Username is taken");
            result.addError(nameTaken);
        }

        if(!customer.getPassword().equals(customer.getRepeatPassword())) {
            FieldError confirmPW = new FieldError("user", "repeatPassword", "Please enter the same password");
            result.addError(confirmPW);
        }

        if(result.hasErrors()) {
            model.addAttribute("customer", customer);
            return  "admin/addUpdateCustomer";
        }

        String encodePW = passwordEncoder.encode(customer.getPassword());
        System.out.println(encodePW);
        customer.setPassword(encodePW);
        userRepository.save(customer);

        return "redirect:/admin/customers";
    }

    @GetMapping("/admin/customers/{id}")
    public String showEditCustomer(@PathVariable("id") Integer id, Model model) {
        User customer = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        model.addAttribute("customer", customer);

        return "admin/addUpdateCustomer";
    }

    @PutMapping("/admin/customers/{id}")
    public String updateCustomer(@PathVariable("id") Integer id, @Valid @ModelAttribute("customer") User customer, BindingResult result, Model model) {
        boolean exist = userRepository.findByUsername(customer.getUsername()).filter(user -> !user.getId().equals(id)).isPresent();
        if(exist) {
            FieldError nameTaken = new FieldError("user", "username", "Username is taken");
            result.addError(nameTaken);
        }

        if(!customer.getPassword().equals(customer.getRepeatPassword())) {
            FieldError confirmPW = new FieldError("user", "repeatPassword", "Please enter the same password");
            result.addError(confirmPW);
        }

        if(result.hasErrors()) {
            model.addAttribute("customer", customer);
            return  "admin/addUpdateCustomer";
        }

        String encodePW = passwordEncoder.encode(customer.getPassword());
        System.out.println(encodePW);
        customer.setPassword(encodePW);
        userRepository.save(customer);

        return "redirect:/admin/customers";
    }
}
