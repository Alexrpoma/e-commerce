package com.ecommerce.customer.exceptions;

public class DuplicateDataException extends RuntimeException {
  public DuplicateDataException(String message) {
    super(message);
  }
}
