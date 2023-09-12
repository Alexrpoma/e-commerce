package com.ecommerce.fraud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class FraudCheckHistory {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID uuid;
  private UUID customer_uuid;
  private boolean isFraudster;
  private LocalDateTime createdAt;
}
