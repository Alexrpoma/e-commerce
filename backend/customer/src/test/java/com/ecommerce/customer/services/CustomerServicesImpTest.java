package com.ecommerce.customer.services;

import com.ecommerce.customer.client.FraudCheckHistoryClient;
import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import com.ecommerce.customer.utils.dtos.CustomerDTO;
import com.ecommerce.customer.utils.dtos.CustomerDTOMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
  @Mock
  private FraudCheckHistoryClient fraudCheckHistoryClient;
  private AutoCloseable autoCloseable;
  private CustomerService customerServiceUnderTest;

  private Customer getCustomer() {
    return Customer.builder()
        .uuid(UUID.randomUUID())
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
    CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();
    //TODO: fix inject of these new dependencies.
    customerServiceUnderTest = new CustomerServicesImp(repository, fraudCheckHistoryClient, customerDTOMapper);
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
    when(repository.saveAndFlush(customer)).thenReturn(customer);
    //then
    CustomerDTO expect = customerServiceUnderTest.createCustomer(customer);
    ArgumentCaptor<Customer> customerArgCaptor = ArgumentCaptor.forClass(Customer.class);

    verify(repository, times(1)).saveAndFlush(customerArgCaptor.capture());
    assertThat(customerArgCaptor.getValue()).isEqualTo(customer);
    assertNotNull(expect);
    assertEquals(expect.lastName(), customer.getLastName());
  }

  @Test
  void itShouldUpdateCustomer() {
    //given
    Customer existingCustomer = getCustomer();
    UUID uuid = existingCustomer.getUuid();
    Customer updateCustomer = getCustomer();
    updateCustomer.setFirstName("Anna");
    updateCustomer.setLastName("Garden");
    updateCustomer.setUsername("anna");
    updateCustomer.setEmail("anna@gmail.com");
    //when
    when(repository.findById(uuid)).thenReturn(Optional.of(existingCustomer));
    when(repository.existCustomerByEmail(any(String.class))).thenReturn(false);
    when(repository.existCustomerByUsername(any(String.class))).thenReturn(false);
    when(repository.save(existingCustomer)).thenReturn(updateCustomer);

    ArgumentCaptor<Customer> customerArgCaptor = ArgumentCaptor.forClass(Customer.class);
    CustomerDTO expect = customerServiceUnderTest.updateCustomer(uuid, updateCustomer);

    verify(repository, times(1)).save(customerArgCaptor.capture());
    assertEquals(customerArgCaptor.getValue(), existingCustomer);
    assertEquals(expect.getClass(), CustomerDTO.class);
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