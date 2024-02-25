package com.ms.cart.controllers;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.dto.body.CartBody;
import com.ms.cart.services.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getById(@PathVariable long id) {
        return new ResponseEntity<>(cartService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CartBody cartBody) {
        List<String> errors = new ArrayList<>();

        CartDTO cartDTO = cartService.registerCart(cartBody, errors);

        return CollectionUtils.isEmpty(errors) ?
                new ResponseEntity<>(cartDTO, HttpStatus.OK) :
                new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}
