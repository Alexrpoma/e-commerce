package com.ecommerce.customer.utils.validators;

import com.ecommerce.customer.exceptions.UUIDInvalidException;

import java.util.UUID;

public class UUIDValidator {
  public static UUID apply(String uuid) {
    try {
      return UUID.fromString(uuid);
    } catch (IllegalArgumentException e) {
      throw new UUIDInvalidException(e.getMessage());
    }
  }
}
