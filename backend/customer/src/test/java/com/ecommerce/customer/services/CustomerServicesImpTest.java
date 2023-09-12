package com.ecommerce.customer.services;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CustomerServicesImpTest {

  @Mock
  private CustomerRepository repository;
  private AutoCloseable autoCloseable;
  private CustomerService customerServiceUnderTest;

  private Customer getCustomer() {
    return Customer.builder()
        .firstName("John")
        .lastName("Doe")
        .username("jonny")
        .email("john@gmail.com")
        .password("12313")
        .build();
  }

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    customerServiceUnderTest = new CustomerServicesImp(repository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void itShouldGetAllCustomers() {
    //when
    customerServiceUnderTest.allCustomers();
    //then
    verify(repository).findAll();
  }

  @Test
  void itShouldGetACustomer() {
    //given
    UUID uuid = UUID.randomUUID();
    Customer customer = getCustomer();
    customer.setUuid(uuid);
    given(repository.findById(uuid)).willReturn(Optional.of(customer));
    //when
    customerServiceUnderTest.getCustomer(uuid);
    //then
    verify(repository, times(1)).findById(uuid);
  }

  @Test
  void itShouldCreateCustomer() {
    //given
    Customer customer = getCustomer();
    //when
    when(repository.existCustomerByEmail(any())).thenReturn(false);
    when(repository.existCustomerByUsername(any())).thenReturn(false);
    when(repository.save(customer)).thenReturn(customer);
    //then
    Customer expect = customerServiceUnderTest.createCustomer(customer);
    ArgumentCaptor<Customer> customerArgCaptor = ArgumentCaptor.forClass(Customer.class);

    verify(repository, times(1)).save(customerArgCaptor.capture());
    assertThat(customerArgCaptor.getValue()).isEqualTo(customer);
    assertNotNull(expect);
    assertEquals(LocalDateTime.class, expect.getRegisteredAt().getClass());
  }

  @Test
  void itShouldUpdateCustomer() {
    Customer customer = getCustomer();

    when(repository.findById(customer.getUuid())).thenReturn(Optional.of(customer));
    when(repository.existCustomerByEmail(any())).thenReturn(false);
    when(repository.existCustomerByUsername(any())).thenReturn(false);
    when(repository.save(any())).thenReturn(customer);

    customer.setFirstName("Anna");
    customer.setLastName("Garden");
    customer.setUsername("anna");
    customer.setEmail("anna@gmail.com");

    Customer expect = customerServiceUnderTest.updateCustomer(customer.getUuid(), customer);
    ArgumentCaptor<Customer> customerArgCaptor = ArgumentCaptor.forClass(Customer.class);

    verify(repository, times(1)).save(customerArgCaptor.capture());
    assertThat(customerArgCaptor.getValue()).isEqualTo(customer);
    assertNotNull(expect);
    assertNotEquals(getCustomer().getFirstName(), expect.getFirstName());
    assertNotEquals(getCustomer().getLastName(), expect.getLastName());
    assertNotEquals(getCustomer().getUsername(), expect.getUsername());
    assertNotEquals(getCustomer().getEmail(), expect.getEmail());
  }

  @Test
  void itShouldDeleteCustomer() {
    UUID uuid = UUID.randomUUID();
    Customer customer = new Customer();
    when(repository.findById(uuid)).thenReturn(Optional.of(customer));
    customerServiceUnderTest.deleteCustomer(uuid);
    verify(repository, times(1)).deleteById(uuid);
  }
}