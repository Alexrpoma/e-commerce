package com.ecommerce.customer.services;

import com.ecommerce.customer.exceptions.DuplicateDataException;
import com.ecommerce.customer.exceptions.NotFoundException;
import com.ecommerce.customer.exceptions.RequestValidationException;
import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    customer.setRegisteredAt(LocalDateTime.now());
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(UUID uuid, Customer updateCustomer) {
    boolean isUpdated = false;
    Customer customer = customerRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException("Customer %s not found!"
            .formatted(uuid)));
    if(customerRepository.existCustomerByEmail(customer.getEmail())) {
      log.error("Update customer: Email %s duplicated".formatted(customer.getEmail()));
      throw new DuplicateDataException("Email %s has already been registered!"
          .formatted(customer.getEmail()));
    }
    if(customerRepository.existCustomerByUsername(customer.getUsername())) {
      log.error("Insert customer: Username %s duplicated".formatted(customer.getUsername()));
      throw new DuplicateDataException("Username %s has already been registered!"
          .formatted(customer.getUsername()));
    }
    if(updateCustomer.getFistName() != null && !updateCustomer.getFistName().equals(customer.getFistName())) {
      customer.setFistName(updateCustomer.getFistName());
      isUpdated = true;
    }
    if(updateCustomer.getLastName() != null && !updateCustomer.getLastName().equals(customer.getLastName())) {
      customer.setLastName(updateCustomer.getLastName());
      isUpdated = true;
    }
    if(updateCustomer.getUsername() != null) {
      customer.setUsername(updateCustomer.getUsername());
      isUpdated = true;
    }
    if(updateCustomer.getEmail() != null) {
      customer.setEmail(updateCustomer.getEmail());
      isUpdated = true;
    }
    if(!isUpdated) {
      throw new RequestValidationException("No data changes found");
    }
    return customerRepository.save(customer);
  }

  @Override
  public void deleteCustomer(UUID uuid) {
    customerRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException("Customer %s not found!"
            .formatted(uuid)));
    customerRepository.deleteById(uuid);
  }
}
