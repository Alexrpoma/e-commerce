package com.ecommerce.fraud.repositories;

import com.ecommerce.fraud.models.FraudCheckHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FraudCheckHistoryRepositoryTest {
  @Autowired
  private FraudCheckHistoryRepository repositoryTest;

  @AfterEach
  void tearDown() {
    repositoryTest.deleteAll();
  }
  private FraudCheckHistory getFraudCheckHistory() {
    return FraudCheckHistory.builder()
        .uuid(UUID.randomUUID())
        .customer_uuid(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .isFraudster(false)
        .build();
  }
  @Test
  void fraudCheckHistoryByCustomerId() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    repositoryTest.save(fraudCheckHistory);
    var expect = repositoryTest.fraudCheckHistoryByCustomerId(fraudCheckHistory.getCustomer_uuid());
    assertThat(expect.isPresent()).isTrue();
    expect.ifPresent(checkHistory -> assertThat(checkHistory.getClass()).isEqualTo(fraudCheckHistory.getClass()));
    expect.ifPresent(checkHistory -> assertEquals(checkHistory.getCustomer_uuid(), expect.get().getCustomer_uuid()));
  }

  @Test
  void existFraudCheckRecordByCustomerId() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    repositoryTest.save(fraudCheckHistory);
    assertTrue(repositoryTest.existFraudCheckRecordByCustomerId(fraudCheckHistory.getCustomer_uuid()));
  }

  @Test
  void deleteFraudCheckRecordByCustomerId() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    repositoryTest.save(fraudCheckHistory);
    repositoryTest.deleteFraudCheckRecordByCustomerId(fraudCheckHistory.getCustomer_uuid());
    assertFalse(repositoryTest.existFraudCheckRecordByCustomerId(fraudCheckHistory.getCustomer_uuid()));
  }
}