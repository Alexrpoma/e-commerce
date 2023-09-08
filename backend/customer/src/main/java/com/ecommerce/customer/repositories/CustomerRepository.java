package com.ecommerce.customer.repositories;

import com.ecommerce.customer.models.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
  @Query("SELECT CASE WHEN Count(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.email =?1")
  boolean existCustomerByEmail(String email);
  @Query("SELECT CASE WHEN Count(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.username =?1")
  boolean existCustomerByUsername(String username);
}