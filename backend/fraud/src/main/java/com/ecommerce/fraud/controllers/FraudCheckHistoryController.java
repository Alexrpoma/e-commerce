package com.ecommerce.fraud.controllers;

import com.ecommerce.fraud.models.FraudCheckHistory;
import com.ecommerce.fraud.services.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fraud-check")
public class FraudCheckHistoryController {

  private final FraudCheckHistoryService service;

  @GetMapping
  public ResponseEntity<List<FraudCheckHistory>> getAll() {
    return ResponseEntity.ok(service.fraudChecklist());
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<FraudCheckHistory> getByCustomerId(@PathVariable UUID customerId) {
    return ResponseEntity.ok(service.getFraudCheckerByCustomerId(customerId));
  }

  @PostMapping("/{customerId}")
  public ResponseEntity<FraudCheckHistory> create(@PathVariable UUID customerId) {
    System.out.println("POST:");
    System.out.println("Fraud check: " + customerId);
    return ResponseEntity.ok(service.insertFraudCheckRecord(customerId));
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> deleteByCustomerId(@PathVariable("customerId") UUID customerId) {
    service.deleteFraudCheckRecordByCustomerId(customerId);
    return ResponseEntity.ok().build();
  }
}
