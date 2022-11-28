package com.ms.cart.services.implementations;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.mapper.ICartDTOMapper;
import com.ms.cart.repositories.ICartRepository;
import com.ms.cart.services.interfaces.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class CartServiceImpl implements ICartService {
    ICartRepository iCartRepository;
    ICartDTOMapper iCartDTOMapper;

    public CartServiceImpl(ICartRepository iCartRepository, ICartDTOMapper iCartDTOMapper) {
        this.iCartRepository = iCartRepository;
        this.iCartDTOMapper = iCartDTOMapper;
    }

    @Override
    @Transactional
    public CartDTO getById(Long id) {
        return iCartDTOMapper.cartToCartDTO(
                iCartRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the cart with id = " + id)));
    }
}
