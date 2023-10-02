package com.ecommerce.fraud.services;

import com.ecommerce.fraud.exceptions.NotFoundException;
import com.ecommerce.fraud.models.FraudCheckHistory;
import com.ecommerce.fraud.repositories.FraudCheckHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FraudCheckHistoryServiceImpTest {

  @Mock
  private FraudCheckHistoryRepository repository;
  @InjectMocks
  private FraudCheckHistoryServiceImp serviceImpTest;
  private AutoCloseable autoCloseable;
  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
  }
  private FraudCheckHistory getFraudCheckHistory() {
    return FraudCheckHistory.builder()
        .uuid(UUID.randomUUID())
        .customer_uuid(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .isFraudster(false)
        .build();
  }
  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void fraudChecklist() {
    serviceImpTest.fraudChecklist();
    verify(repository).findAll();
  }

  @Test
  void getFraudCheck() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    when(repository.findById(any()))
        .thenReturn(Optional.of(fraudCheckHistory));
    var expect = serviceImpTest.getFraudCheck(fraudCheckHistory.getUuid());
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(repository).findById(uuidArgumentCaptor.capture());
    assertThat(expect).isNotNull();
    assertThat(expect.getClass()).isEqualTo(fraudCheckHistory.getClass());
  }

  @Test
  void getFraudCheckerByCustomerId() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    when(repository.fraudCheckHistoryByCustomerId(any()))
        .thenReturn(Optional.ofNullable(fraudCheckHistory));
    assert fraudCheckHistory != null;
    var expect = serviceImpTest.getFraudCheckerByCustomerId(fraudCheckHistory.getCustomer_uuid());
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(repository).fraudCheckHistoryByCustomerId(uuidArgumentCaptor.capture());
    assertEquals(expect.getCustomer_uuid(), fraudCheckHistory.getCustomer_uuid());
  }

  @Test
  void itShouldThrowNotFoundException() {
    UUID uuid = UUID.randomUUID();
    assertThatThrownBy(() -> serviceImpTest.getFraudCheck(uuid))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("%s fraud check history not found"
            .formatted(uuid));
    assertThatThrownBy(() -> serviceImpTest.getFraudCheckerByCustomerId(uuid))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("fraud check history by customer id %s not found".formatted(uuid));
  }

  @Test
  void insertFraudCheckRecord() {
    FraudCheckHistory fraudCheckHistory = getFraudCheckHistory();
    when(repository.save(any())).thenReturn(fraudCheckHistory);
    ArgumentCaptor<FraudCheckHistory> fraudCheckHistoryArgumentCaptor =
        ArgumentCaptor.forClass(FraudCheckHistory.class);
    var expected = serviceImpTest.insertFraudCheckRecord(fraudCheckHistory.getCustomer_uuid());
    verify(repository).save(fraudCheckHistoryArgumentCaptor.capture());
    assertThat(expected.getClass()).isEqualTo(fraudCheckHistoryArgumentCaptor.getValue().getClass());
  }

  @Test
  void deleteFraudCheckRecordByCustomerId() {
    when(repository.existFraudCheckRecordByCustomerId(any())).thenReturn(true);
    serviceImpTest.deleteFraudCheckRecordByCustomerId(UUID.randomUUID());
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(repository).deleteFraudCheckRecordByCustomerId(uuidArgumentCaptor.capture());
  }

}