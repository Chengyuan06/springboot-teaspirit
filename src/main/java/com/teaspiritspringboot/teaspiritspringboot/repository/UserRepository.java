package com.teaspiritspringboot.teaspiritspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teaspiritspringboot.teaspiritspringboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE (u.username = :identifier OR u.email = :identifier) AND u.password = :password")
    Optional<User> findByIdentifierAndPassword(@Param("identifier") String identifier, @Param("password") String password);


    
}
