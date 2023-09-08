package com.ecommerce.customer.services;

import com.ecommerce.customer.exceptions.DuplicateDataException;
import com.ecommerce.customer.exceptions.NotFoundException;
import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
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
    if(customerRepository.existCustomerByEmail(customer.getEmail())) {
      log.error("Insert customer: Email %s duplicated".formatted(customer.getEmail()));
      throw new DuplicateDataException("Email %s has already been registered!"
          .formatted(customer.getEmail()));
    }
    if(customerRepository.existCustomerByUsername(customer.getUsername())) {
      log.error("Insert customer: Username %s duplicated".formatted(customer.getUsername()));
      throw new DuplicateDataException("Username %s has already been registered!"
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
