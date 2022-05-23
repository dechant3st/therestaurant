package com.therestaurant.de.demo.therestaurant.service;

import com.therestaurant.de.demo.therestaurant.entity.User;
import com.therestaurant.de.demo.therestaurant.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user.get();
    }

    @Transactional
    public String register(@Valid User user, BindingResult result, Model model) {
        boolean exist = userRepository.findByUsername(user.getUsername()).isPresent();
        if(exist) {
            FieldError nameTaken = new FieldError("user", "username", "Username is taken");
            result.addError(nameTaken);
        }

        if(!user.getPassword().equals(user.getRepeatPassword())) {
            FieldError confirmPW = new FieldError("user", "confirmPassword", "Please enter the same password");
            result.addError(confirmPW);
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return  "register";
        }

        String encodePW = passwordEncoder.encode(user.getPassword());
        System.out.println(encodePW);
        user.setRole("CUSTOMER");
        user.setPassword(encodePW);
        userRepository.save(user);

        return "redirect:/login";
    }
}
