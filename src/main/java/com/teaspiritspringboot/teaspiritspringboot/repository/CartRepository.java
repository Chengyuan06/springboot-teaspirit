package com.teaspiritspringboot.teaspiritspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.teaspiritspringboot.teaspiritspringboot.model.Cart;
import com.teaspiritspringboot.teaspiritspringboot.model.User;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    Optional<Cart> findBySessionId(String sessionId);
}
