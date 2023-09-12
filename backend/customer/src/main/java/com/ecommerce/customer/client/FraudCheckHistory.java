package com.ecommerce.customer.client;

import java.time.LocalDateTime;
import java.util.UUID;

public record FraudCheckHistory(
    UUID uuid,
    UUID customer_uuid,
    boolean isFraudster,
    LocalDateTime createdAt
) {
}
