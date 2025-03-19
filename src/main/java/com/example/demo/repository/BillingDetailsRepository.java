package com.example.demo.repository;
import com.example.demo.model.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {

    Optional<BillingDetails> findByUserId(Long userId);
}
