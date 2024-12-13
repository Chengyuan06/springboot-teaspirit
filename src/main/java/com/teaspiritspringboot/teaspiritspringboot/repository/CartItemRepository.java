package com.teaspiritspringboot.teaspiritspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teaspiritspringboot.teaspiritspringboot.model.CartItem;
import com.teaspiritspringboot.teaspiritspringboot.model.CartItemId;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId>{

    Optional<CartItem> findById(CartItemId cartItemId);
    
}
