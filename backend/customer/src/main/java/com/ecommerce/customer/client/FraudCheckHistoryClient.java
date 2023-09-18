package com.ecommerce.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static com.ecommerce.customer.utils.constants.Path.API_FRAUD_CHECK_CUSTOMER_ID;
import static com.ecommerce.customer.utils.constants.Path.CUSTOMER_ID;

@FeignClient(name = "fraud")
public interface FraudCheckHistoryClient {

  @PostMapping(API_FRAUD_CHECK_CUSTOMER_ID)
  void createFraudCheckRecord(@PathVariable(CUSTOMER_ID) UUID customerId);

  @DeleteMapping(API_FRAUD_CHECK_CUSTOMER_ID)
  void deleteFraudCheckRecord(@PathVariable(CUSTOMER_ID) UUID customerId);
}
