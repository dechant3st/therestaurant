package com.therestaurant.de.demo.therestaurant.repo;

import com.therestaurant.de.demo.therestaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
