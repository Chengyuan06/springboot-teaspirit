package com.teaspiritspringboot.teaspiritspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.teaspiritspringboot.teaspiritspringboot.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE (u.username = :identifier OR u.email = :identifier)")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);


    
}
