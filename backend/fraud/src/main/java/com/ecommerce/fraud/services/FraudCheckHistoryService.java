package com.ecommerce.fraud.services;

import com.ecommerce.fraud.models.FraudCheckHistory;

import java.util.List;
import java.util.UUID;

public interface FraudCheckHistoryService {
  List<FraudCheckHistory> fraudChecklist();
  FraudCheckHistory getFraudCheck(UUID uuid);
  FraudCheckHistory getFraudCheckerByCustomerId(UUID customerUuid);
  FraudCheckHistory insertFraudCheckHistory(FraudCheckHistory fraudCheckHistory);
  FraudCheckHistory updateFraudChecker(UUID uuid, FraudCheckHistory fraudCheckHistory);
}
