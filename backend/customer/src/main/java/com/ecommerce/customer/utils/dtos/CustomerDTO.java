package com.ecommerce.customer.utils.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerDTO(
    UUID id,
    String firstName,
    String lastName,
    String username,
    String email
) {
}
