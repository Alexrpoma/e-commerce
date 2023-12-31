package com.ecommerce.customer.controllers;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.services.CustomerService;
import com.ecommerce.customer.utils.dtos.CustomerDTO;
import com.ecommerce.customer.utils.validators.UUIDValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public record CustomerController(CustomerService service) {
  @GetMapping
  public ResponseEntity<List<CustomerDTO>> all() {
    return ResponseEntity.ok(service.allCustomers());
  }
  @GetMapping("/{uuid}")
  public ResponseEntity<CustomerDTO> get(@PathVariable String  uuid) {
    return ResponseEntity.ok(service.getCustomer(UUIDValidator.apply(uuid)));
  }
  @PostMapping
  public ResponseEntity<CustomerDTO> create(@RequestBody Customer customer) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(service.createCustomer(customer));
  }
  @PutMapping("/{uuid}")
  public ResponseEntity<CustomerDTO> update(@PathVariable String uuid, @RequestBody Customer customer) {
    return ResponseEntity.ok(service.updateCustomer(UUIDValidator.apply(uuid), customer));
  }
  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> delete(@PathVariable String  uuid) {
    service.deleteCustomer(UUIDValidator.apply(uuid));
    return ResponseEntity.ok().build();
  }
}
