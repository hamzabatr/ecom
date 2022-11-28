package com.ms.product.controllers;

import com.ms.product.dto.ProductDTO;
import com.ms.product.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @PutMapping
    public ResponseEntity<ProductDTO> createOrUpdate(@RequestBody ProductDTO productDTO) {
        ProductDTO product = productService.createOrUpdate(productDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        List<ProductDTO> productList = productService.listAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        ProductDTO product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
