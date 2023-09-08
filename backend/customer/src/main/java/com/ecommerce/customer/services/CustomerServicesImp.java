package com.ecommerce.customer.services;

import com.ecommerce.customer.exceptions.DuplicateDataException;
import com.ecommerce.customer.exceptions.NotFoundException;
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
    return customerRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException("Customer %s not found!"
            .formatted(uuid)));
  }

  @Override
  public Customer createCustomer(Customer customer) {
    if(customerRepository.existCustomerEmail(customer.getEmail())) {
      throw new DuplicateDataException("Email %s already has been taken!"
          .formatted(customer.getEmail()));
    }
    if(customerRepository.existCustomerUsername(customer.getUsername())) {
      throw new DuplicateDataException("Username %s already has been taken!"
          .formatted(customer.getUsername()));
    }
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(UUID uuid, Customer customer) {
    return null;
  }

  @Override
  public void deleteCustomer(UUID uuid) {

  }
}
