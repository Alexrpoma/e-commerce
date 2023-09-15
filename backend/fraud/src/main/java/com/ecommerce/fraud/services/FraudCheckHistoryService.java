package com.ecommerce.fraud.services;

import com.ecommerce.fraud.models.FraudCheckHistory;

import java.util.List;
import java.util.UUID;

public interface FraudCheckHistoryService {
  List<FraudCheckHistory> fraudChecklist();
  FraudCheckHistory getFraudCheck(UUID uuid);
  FraudCheckHistory getFraudCheckerByCustomerId(UUID customerId);
  FraudCheckHistory insertFraudCheckRecord(UUID customerId);
  void deleteFraudCheckRecordByCustomerId(UUID customerId);
  FraudCheckHistory updateFraudChecker(UUID uuid, FraudCheckHistory fraudCheckHistory);
}
