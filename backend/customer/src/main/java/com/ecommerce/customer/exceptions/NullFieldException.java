package com.ecommerce.customer.exceptions;

public class NullFieldException extends RuntimeException {
  public NullFieldException(String field) {
    super("The %s field is Null".formatted(field));
  }
}
