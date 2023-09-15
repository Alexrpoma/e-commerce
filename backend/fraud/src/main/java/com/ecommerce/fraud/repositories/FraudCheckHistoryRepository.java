package com.ecommerce.fraud.repositories;

import com.ecommerce.fraud.models.FraudCheckHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

@Transactional
public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, UUID> {
  @Query("select f from FraudCheckHistory f where f.customer_uuid = ?1")
  Optional<FraudCheckHistory> fraudCheckHistoryByCustomerId(UUID uuid);
  @Query("select case when count(f) > 0 then true else false end from FraudCheckHistory f where f.customer_uuid = ?1")
  boolean existFraudCheckRecordByCustomerId(UUID customerId);
  @Modifying
  @Query("delete from FraudCheckHistory f where f.customer_uuid = ?1")
  void deleteFraudCheckRecordByCustomerId(UUID customerId);
}