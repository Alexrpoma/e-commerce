package com.ecommerce.customer.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handler(
      NotFoundException notFoundException,
      HttpServletRequest request
  ) {
    ApiError apiError = ApiError.builder()
        .path(request.getRequestURI())
        .message(notFoundException.getMessage())
        .statusCode(HttpStatus.NOT_FOUND.value())
        .localDateTime(getLocalTime())
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
  }

  @ExceptionHandler(DuplicateDataException.class)
  public ResponseEntity<ApiError> handler(
      DuplicateDataException duplicateDataException,
      HttpServletRequest request
  ) {
    ApiError apiError = ApiError.builder()
        .path(request.getRequestURI())
        .message(duplicateDataException.getMessage())
        .statusCode(HttpStatus.CONFLICT.value())
        .localDateTime(getLocalTime())
        .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
  }

  @ExceptionHandler(RequestValidationException.class)
  public ResponseEntity<ApiError> handler(
      RequestValidationException validationException,
      HttpServletRequest request
  ) {
    ApiError apiError = ApiError.builder()
        .path(request.getRequestURI())
        .message(validationException.getMessage())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .localDateTime(getLocalTime())
        .build();
    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(NullFieldException.class)
  public ResponseEntity<ApiError> handler(
      NullFieldException nullFieldException,
      HttpServletRequest request
  ) {
    ApiError apiError = ApiError.builder()
        .path(request.getRequestURI())
        .message(nullFieldException.getMessage())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .localDateTime(getLocalTime())
        .build();
    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(UUIDInvalidException.class)
  public ResponseEntity<ApiError> handler(
      UUIDInvalidException uuidInvalidException,
      HttpServletRequest request
  ) {
    ApiError apiError = ApiError.builder()
        .path(request.getRequestURI())
        .message(uuidInvalidException.getMessage())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .localDateTime(getLocalTime())
        .build();
    return ResponseEntity.badRequest().body(apiError);
  }

  private String getLocalTime() {
    return LocalDateTime
        .now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
