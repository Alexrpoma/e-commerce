package com.ecommerce.fraud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckHistory {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID uuid;
  private UUID customer_uuid;
  private boolean isFraudster;
  private LocalDateTime createdAt;
}
