package com.ecommerce.customer.services;

import com.ecommerce.customer.client.FraudCheckHistoryClient;
import com.ecommerce.customer.exceptions.DuplicateDataException;
import com.ecommerce.customer.exceptions.NotFoundException;
import com.ecommerce.customer.exceptions.NullFieldException;
import com.ecommerce.customer.exceptions.RequestValidationException;
import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import com.ecommerce.customer.utils.dtos.CustomerDTO;
import com.ecommerce.customer.utils.dtos.CustomerDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.ecommerce.customer.utils.constants.CustomerConstants.Service.*;

@Slf4j
@Service
public record CustomerServicesImp(
    CustomerRepository customerRepository,
    FraudCheckHistoryClient fraudCheckHistoryClient,
    CustomerDTOMapper customerDTOMapper

) implements CustomerService {

  @Override
  public List<CustomerDTO> allCustomers() {
    return customerRepository.findAll()
        .stream().map(customerDTOMapper).toList();
  }

  @Override
  public CustomerDTO getCustomer(UUID uuid) {
    return customerDTOMapper.apply(findCustomerByID(uuid));
  }

  @Override
  public CustomerDTO createCustomer(Customer customer) {
    notNullFieldsValidator(customer);
    duplicateEmailUsernameValidator(customer, false);
    customer.setRegisteredAt(LocalDateTime.now());
    Customer createdCustomer = customerRepository.saveAndFlush(customer);
    fraudCheckHistoryClient.createFraudCheckRecord(customer.getUuid());
    return customerDTOMapper.apply(createdCustomer);
  }

  @Override
  public CustomerDTO updateCustomer(UUID uuid, Customer updateCustomer) {
    boolean isUpdated = false;
    Customer customer = findCustomerByID(uuid);
    duplicateEmailUsernameValidator(updateCustomer, true);
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
    return customerDTOMapper.apply(customerRepository.save(customer));
  }

  @Override
  public void deleteCustomer(UUID uuid) {
    findCustomerByID(uuid);
    customerRepository.deleteById(uuid);
  }

  private void duplicateEmailUsernameValidator(Customer customer, boolean isAnUpdate) {
    if(customerRepository.existCustomerByEmail(customer.getEmail())) {
      if (isAnUpdate) log.error(LOG_ERROR_UPDATE_DUPLICATED_EMAIL.formatted(customer.getEmail()));
      else log.error(LOG_ERROR_INSERT_DUPLICATED_EMAIL.formatted(customer.getEmail()));
      throw new DuplicateDataException(CUSTOMER_EMAIL_ALREADY_EXIST
          .formatted(customer.getEmail()));
    }
    if(customerRepository.existCustomerByUsername(customer.getUsername())) {
      if (isAnUpdate) log.error(LOG_ERROR_UPDATE_DUPLICATED_USERNAME.formatted(customer.getUsername()));
      else log.error(LOG_ERROR_INSERT_DUPLICATED_USERNAME.formatted(customer.getUsername()));
      throw new DuplicateDataException(CUSTOMER_USERNAME_ALREADY_EXIST
          .formatted(customer.getUsername()));
    }
  }

  private Customer findCustomerByID(UUID uuid) {
    return customerRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND
            .formatted(uuid)));
  }

  private void notNullFieldsValidator(Customer customer) {
    if (customer.getFirstName() == null) {
      throw new NullFieldException("firstname");
    }
    if (customer.getLastName() == null) {
      throw new NullFieldException("lastname");
    }
    if (customer.getUsername() == null) {
      throw new NullFieldException("username");
    }
    if (customer.getEmail() == null) {
      throw new NullFieldException("email");
    }
    if (customer.getPassword() == null) {
      throw new NullFieldException("password");
    }
  }
}
