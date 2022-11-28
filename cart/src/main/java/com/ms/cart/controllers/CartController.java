package com.ms.cart.controllers;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.services.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getById(@PathVariable Long id) {
        CartDTO product = cartService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
