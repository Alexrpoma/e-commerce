package com.ecommerce.fraud.services;

import com.ecommerce.fraud.models.FraudCheckHistory;
import com.ecommerce.fraud.repositories.FraudCheckHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryServiceImp implements FraudCheckHistoryService{

  private final FraudCheckHistoryRepository repository;

  @Override
  public List<FraudCheckHistory> fraudChecklist() {
    return repository.findAll();
  }

  @Override
  public FraudCheckHistory getFraudCheck(UUID uuid) {
    return repository.findById(uuid)
        .orElseThrow(() -> new RuntimeException("%s fraud check history not found"
            .formatted(uuid)));
  }

  @Override
  public FraudCheckHistory getFraudCheckerByCustomerId(UUID customerId) {
    return repository.fraudCheckHistoryByCustomerId(customerId)
        .orElseThrow(() -> new RuntimeException("fraud check history not found by customer id"));
  }

  @Override
  public FraudCheckHistory insertFraudCheckRecord(UUID customerId) {
    return repository.save(FraudCheckHistory.builder()
        .customer_uuid(customerId)
        .isFraudster(false)
        .createdAt(LocalDateTime.now())
        .build());
  }

  @Override
  public FraudCheckHistory updateFraudChecker(UUID uuid, FraudCheckHistory fraudCheckHistory) {
    return null;
  }
}
