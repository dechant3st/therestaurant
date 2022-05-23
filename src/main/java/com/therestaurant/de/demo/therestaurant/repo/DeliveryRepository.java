package com.therestaurant.de.demo.therestaurant.repo;

import com.therestaurant.de.demo.therestaurant.entity.Delivery;
import com.therestaurant.de.demo.therestaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Optional<Delivery> findByOrder(Order order);
}
