package com.ecommerce.customer.exceptions;

public class RequestValidationException extends RuntimeException {
  public RequestValidationException(String message) {
    super(message);
  }
}
