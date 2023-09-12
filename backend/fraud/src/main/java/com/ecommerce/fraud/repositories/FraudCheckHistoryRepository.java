package com.ecommerce.fraud.repositories;

import com.ecommerce.fraud.models.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, UUID> {
  @Query("select f from FraudCheckHistory f where f.customer_uuid = ?1")
  Optional<FraudCheckHistory> fraudCheckHistoryByCustomerId(UUID uuid);
}