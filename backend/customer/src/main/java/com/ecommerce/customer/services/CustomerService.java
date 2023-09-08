package com.ecommerce.customer.services;

import com.ecommerce.customer.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
  List<Customer> allCustomers();
  Customer getCustomer(UUID uuid);
  Customer createCustomer(Customer customer);
  Customer updateCustomer(UUID uuid, Customer customer);
  void deleteCustomer(UUID uuid);
}
