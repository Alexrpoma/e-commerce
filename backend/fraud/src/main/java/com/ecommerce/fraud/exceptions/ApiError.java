package com.ecommerce.fraud.exceptions;

import lombok.Builder;

@Builder
public record ApiError(
    String path,
    String message,
    int statusCode,
    String LocalDate
) {
}
