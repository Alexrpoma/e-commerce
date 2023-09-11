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

import static com.ecommerce.customer.utils.constants.CustomerConstants.Service.*;

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
    return findCustomerByID(uuid);
  }

  @Override
  public Customer createCustomer(Customer customer) {
    duplicateEmailUsernameValidator(customer);
    customer.setRegisteredAt(LocalDateTime.now());
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(UUID uuid, Customer updateCustomer) {
    boolean isUpdated = false;
    Customer customer = findCustomerByID(uuid);
    duplicateEmailUsernameValidator(updateCustomer);
    if(updateCustomer.getFirstName() != null
        && !updateCustomer.getFirstName().equals(customer.getFirstName())) {
      customer.setFirstName(updateCustomer.getFirstName());
      isUpdated = true;
    }
    if(updateCustomer.getLastName() != null
        && !updateCustomer.getLastName().equals(customer.getLastName())) {
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
      throw new RequestValidationException(NO_DATA_CHANGES_FOUND);
    }
    return customerRepository.save(customer);
  }

  @Override
  public void deleteCustomer(UUID uuid) {
    findCustomerByID(uuid);
    customerRepository.deleteById(uuid);
  }

  private void duplicateEmailUsernameValidator(Customer customer) {
    if(customerRepository.existCustomerByEmail(customer.getEmail())) {
      log.error(LOG_ERROR_INSERT_DUPLICATED_EMAIL.formatted(customer.getEmail()));
      throw new DuplicateDataException(CUSTOMER_EMAIL_ALREADY_EXIST
          .formatted(customer.getEmail()));
    }
    if(customerRepository.existCustomerByUsername(customer.getUsername())) {
      log.error(LOG_ERROR_INSERT_DUPLICATED_USERNAME.formatted(customer.getUsername()));
      throw new DuplicateDataException(CUSTOMER_USERNAME_ALREADY_EXIST
          .formatted(customer.getUsername()));
    }
  }

  private Customer findCustomerByID(UUID uuid) {
    return customerRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND
            .formatted(uuid)));
  }
}
