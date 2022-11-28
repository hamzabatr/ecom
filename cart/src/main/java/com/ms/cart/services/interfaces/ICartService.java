package com.ms.cart.services.interfaces;

import com.ms.cart.dto.CartDTO;

public interface ICartService {
    CartDTO getById(Long id);
}
