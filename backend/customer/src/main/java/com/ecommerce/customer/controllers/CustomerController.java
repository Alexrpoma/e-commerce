package com.ecommerce.customer.controllers;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public record CustomerController(CustomerService service) {
  @GetMapping
  public ResponseEntity<List<Customer>> all() {
    return ResponseEntity.ok(service.allCustomers());
  }
}
