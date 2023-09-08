package com.ecommerce.customer.controllers;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public record CustomerController(CustomerService service) {
  @GetMapping
  public ResponseEntity<List<Customer>> all() {
    return ResponseEntity.ok(service.allCustomers());
  }
  @GetMapping("/{uuid}")
  public ResponseEntity<Customer> get(@PathVariable UUID uuid) {
    return ResponseEntity.ok(service.getCustomer(uuid));
  }
  @PostMapping
  public ResponseEntity<Customer> create(@RequestBody Customer customer) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(service.createCustomer(customer));
  }
  @PutMapping("/{uuid}")
  public ResponseEntity<Customer> update(@PathVariable UUID uuid, @RequestBody Customer customer) {
    return ResponseEntity.ok(service.updateCustomer(uuid, customer));
  }
  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
    service.deleteCustomer(uuid);
    return ResponseEntity.ok().build();
  }
}
