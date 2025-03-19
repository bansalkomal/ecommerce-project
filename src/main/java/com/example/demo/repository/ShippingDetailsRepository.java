package com.example.demo.repository;

import com.example.demo.model.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {

    Optional<ShippingDetails> findByUserId(Long userId);
}
