package com.ecommerce.customer.services;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.utils.dtos.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
  List<CustomerDTO> allCustomers();
  CustomerDTO getCustomer(UUID uuid);
  CustomerDTO createCustomer(Customer customer);
  CustomerDTO updateCustomer(UUID uuid, Customer updateCustomer);
  void deleteCustomer(UUID uuid);
}
