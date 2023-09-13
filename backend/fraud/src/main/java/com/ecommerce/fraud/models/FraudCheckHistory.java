package com.ecommerce.fraud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  private UUID customer_uuid;
  private boolean isFraudster;
  private LocalDateTime createdAt;
}
