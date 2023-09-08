package com.ecommerce.customer.config;

import com.ecommerce.customer.models.Customer;
import com.ecommerce.customer.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {
  @Bean
  CommandLineRunner commandLineRunner(CustomerRepository repository) {
    return args -> {
      Customer customer0 = Customer.builder()
          .fistName("Robert")
          .lastName("Garden")
          .username("robin")
          .email("robert@gmail.com")
          .password("123123")
          .build();
      Customer customer1 = Customer.builder()
          .fistName("Paola")
          .lastName("Montes")
          .username("paola")
          .email("paolat@outlook.com")
          .password("123123")
          .build();
      repository.saveAll(List.of(customer0, customer1));
    };
  }
}
