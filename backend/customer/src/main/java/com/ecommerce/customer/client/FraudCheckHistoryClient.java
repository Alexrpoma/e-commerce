package com.ecommerce.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "fraud", url = "http://localhost:9050/api/v1/froud-check")
public interface FraudCheckHistoryClient {

  @PostMapping("/{customerId")
  FraudCheckHistory fraudCheckHistory(@PathVariable UUID customerId);
}
