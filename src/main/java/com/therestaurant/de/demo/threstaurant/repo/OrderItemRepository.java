package com.therestaurant.de.demo.threstaurant.repo;

import com.therestaurant.de.demo.threstaurant.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
