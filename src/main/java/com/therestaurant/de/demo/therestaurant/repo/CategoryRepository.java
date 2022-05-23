package com.therestaurant.de.demo.therestaurant.repo;

import com.therestaurant.de.demo.therestaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Integer id);
    Optional<Category> findByName(String name);
}
