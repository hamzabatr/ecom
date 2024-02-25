package com.ms.cart.services.interfaces;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.dto.body.CartBody;

import java.util.List;

public interface ICartService {
    CartDTO getById(long id);
    CartDTO registerCart(CartBody cartBody, List<String> errors);
}
