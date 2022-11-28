package com.ms.cart.services.implementations;

import com.ms.cart.mapper.ICartDTOMapper;
import com.ms.cart.repositories.ICartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartServiceImpl {
    ICartRepository iCartRepository;
    ICartDTOMapper iCartDTOMapper;

    public CartServiceImpl(ICartRepository iCartRepository, ICartDTOMapper iCartDTOMapper) {
        this.iCartRepository = iCartRepository;
        this.iCartDTOMapper = iCartDTOMapper;
    }
}
