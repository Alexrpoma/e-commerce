package com.ecommerce.customer.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "customer_email_unique", columnNames = "email"),
        @UniqueConstraint(name = "customer_username_unique", columnNames = "username")
    }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID uuid;
  @Column(nullable = false)
  private String fistName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @DateTimeFormat
  private LocalDateTime registeredAt;
}
