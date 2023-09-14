package com.ecommerce.customer.utils.dtos;

import com.ecommerce.customer.models.Customer;

import java.util.function.Function;

public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

  @Override
  public CustomerDTO apply(Customer customer) {
    return CustomerDTO.builder()
        .id(customer.getUuid())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .username(customer.getUsername())
        .email(customer.getEmail())
        .build();
  }
}
