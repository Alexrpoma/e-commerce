package com.ecommerce.customer.repositories;

import com.ecommerce.customer.models.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repositoryUnderTest;

  private Customer getCustomer() {
    return Customer.builder()
        .firstName("John")
        .lastName("Doe")
        .username("jonny")
        .email("john@gmail.com")
        .password("12313")
        .build();
  }

  @AfterEach
  void tearDown() {
    repositoryUnderTest.deleteAll();
  }

  @Test
  void itShouldCheckIfCustomerEmailExists() {
    //given
    Customer customer = getCustomer();
    String email = customer.getEmail();
    repositoryUnderTest.save(customer);
    //when
    boolean expect = repositoryUnderTest.existCustomerByEmail(email);
    //then
    assertThat(expect).isTrue();
  }

  @Test
  void itShouldCheckIfCustomerEmailDoesNotExists() {
    //given
    Customer customer = getCustomer();
    String email = "robert@outlook.com";
    repositoryUnderTest.save(customer);
    //when
    boolean expect = repositoryUnderTest.existCustomerByEmail(email);
    //then
    assertThat(expect).isFalse();
  }

  @Test
  void itShouldCheckIfCustomerUsernameExists() {
    //given
    Customer customer = getCustomer();
    String username = customer.getUsername();
    repositoryUnderTest.save(customer);
    //when
    boolean expect = repositoryUnderTest.existCustomerByUsername(username);
    //then
    assertThat(expect).isTrue();
  }

  @Test
  void itShouldCheckIfCustomerUsernameDoesNotExists() {
    //given
    Customer customer = getCustomer();
    String username = "robby";
    repositoryUnderTest.save(customer);
    //when
    boolean expect = repositoryUnderTest.existCustomerByUsername(username);
    //then
    assertThat(expect).isFalse();
  }

}