package com.therestaurant.de.demo.threstaurant.repo;

import com.therestaurant.de.demo.threstaurant.composite.CartId;
import com.therestaurant.de.demo.threstaurant.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    @Query(value = "SELECT c.* FROM carts c WHERE c.user_id = ?1 and c.menu_id = ?2", nativeQuery = true)
    Optional<Cart> findCartItem(Integer userId, Integer menuId);

    @Query(value = "SELECT c.* FROM carts c WHERE c.user_id = ?1", nativeQuery = true)
    List<Cart> findAllByUserId(Integer userId);
}
