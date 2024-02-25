package com.ms.user.controllers;

import com.ms.user.dto.ProductDTO;
import com.ms.user.feignclient.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class testController {
    private final ProductFeignClient productFeignClient;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        log.info("Starting BLOCKING Controller!");
        List<ProductDTO> l = productFeignClient.list().getBody();
        assert l != null;
        l.forEach(product -> log.info(product.toString()));
        log.info("Exiting BLOCKING Controller!");
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @GetMapping(value = "/2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDTO>> getProductsNonBlocking() {
        log.info("Starting NON-BLOCKING Controller!");

        Flux<ProductDTO> productFlux = WebClient.create()
                .get()
                .uri("http://localhost:1111/products")
                .retrieve()
                .bodyToFlux(ProductDTO.class);

        productFlux.subscribe(product -> log.info(product.toString()));

        log.info("Exiting NON-BLOCKING Controller!");
        return new ResponseEntity<>(productFlux, HttpStatus.OK);
    }
}
