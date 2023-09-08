package com.ecommerce.customer.services;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record CustomerServicesImp(
    CustomerRepository customerRepository
) implements CustomerService {
  @Override
  public List<Customer> allCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer getCustomer(UUID uuid) {
    return null;
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return null;
  }

  @Override
  public Customer updateCustomer(UUID uuid, Customer customer) {
    return null;
  }

  @Override
  public void deleteCustomer(UUID uuid) {

  }
}
