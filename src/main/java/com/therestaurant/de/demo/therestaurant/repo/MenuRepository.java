package com.therestaurant.de.demo.therestaurant.repo;

import com.therestaurant.de.demo.therestaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
