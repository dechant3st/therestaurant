package com.therestaurant.de.demo.therestaurant.repo;

import com.therestaurant.de.demo.therestaurant.entity.Cart;
import com.therestaurant.de.demo.therestaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUser(User user);
}
