package com.ecommerce.fraud.services;

import com.ecommerce.fraud.models.FraudCheckHistory;

import java.util.List;
import java.util.UUID;

public class FraudCheckHistoryServiceImp implements FraudCheckHistoryService{
  @Override
  public List<FraudCheckHistory> fraudChecklist() {
    return null;
  }

  @Override
  public FraudCheckHistory getFraudCheck(UUID uuid) {
    return null;
  }

  @Override
  public FraudCheckHistory getFraudCheckerByCustomerId(UUID customerUuid) {
    return null;
  }

  @Override
  public FraudCheckHistory insertFraudCheckHistory(FraudCheckHistory fraudCheckHistory) {
    return null;
  }

  @Override
  public FraudCheckHistory updateFraudChecker(UUID uuid, FraudCheckHistory fraudCheckHistory) {
    return null;
  }
}
