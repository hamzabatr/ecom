package com.ms.user.feignclient;

import com.ms.user.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "products", url = "http://localhost:1111")
public interface ProductFeignClient {
    @GetMapping(value = "/products", produces = "application/json")
    ResponseEntity<List<ProductDTO>> list();
}
