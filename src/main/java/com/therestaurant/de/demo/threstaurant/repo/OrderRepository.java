package com.therestaurant.de.demo.threstaurant.repo;

import com.therestaurant.de.demo.threstaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
