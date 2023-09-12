package com.ecommerce.fraud.repositories;

import com.ecommerce.fraud.models.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, UUID> {
}