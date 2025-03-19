package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @EntityGraph(attributePaths = {"billingDetails", "shippingDetails"})
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.billingDetails LEFT JOIN FETCH u.shippingDetails WHERE u.id = :id")
    Optional<User> findByIdWithDetails(@Param("id") Long id);

}
