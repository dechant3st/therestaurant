package com.therestaurant.de.demo.threstaurant.repo;

import com.therestaurant.de.demo.threstaurant.entity.Cart;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Optional<Menu> findById(Integer id);

    List<User> findAllByCategory(Category category);
}
