package com.ms.cart.services.implementations;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.dto.body.CartBody;
import com.ms.cart.mapper.ICartDTOMapper;
import com.ms.cart.mapper.IItemDTOMapper;
import com.ms.cart.models.Cart;
import com.ms.cart.repositories.ICartRepository;
import com.ms.cart.repositories.IItemRepository;
import com.ms.cart.services.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ICartRepository iCartRepository;
    @Autowired
    private IItemRepository iItemRepository;

    @Autowired
    private ICartDTOMapper iCartDTOMapper;

    @Autowired
    private IItemDTOMapper iItemDTOMapper;

    @Override
    @Transactional(readOnly = true)
    public CartDTO getById(long id) {
        return iCartDTOMapper.cartToCartDTO(
                iCartRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the cart with id = " + id)));
    }

    @Override
    @Transactional
    public CartDTO registerCart(CartBody cartBody, List<String> errors) {
        return iCartDTOMapper.cartToCartDTO(
                Optional.ofNullable(cartBody)
                        .map(body -> handleCartBody(body, errors))
                        .orElse(null)
        );
    }

    private Cart handleCartBody(CartBody cartBody, List<String> errors) {
        return Optional.ofNullable(cartBody)
                .map(body -> {
                    validateCartBody(body, errors);
                    return CollectionUtils.isEmpty(errors) ? createCartWithItemsIfNotExists(body) : null;
                })
                .orElse(null);
    }

    private void validateCartBody(CartBody cartBody, List<String> errors) {
//        if (CollectionUtils.isEmpty(errors)) {
//
//        }
    }

    private Cart createCartWithItemsIfNotExists(CartBody cartBody) {
        return iCartRepository.findById(cartBody.getCartId())
                .map(cart -> {
                    cart.setItems(iItemDTOMapper.itemsDTOToItems(cartBody.getItems(), cart));
                    return iCartRepository.save(cart);
                })
                .orElse(
                        iItemRepository.saveAll(
                                        iItemDTOMapper.itemsDTOToItems(
                                                cartBody.getItems(),
                                                iCartRepository.save(
                                                        Cart.builder()
                                                                .userId(cartBody.getUserId())
                                                                .reduction(cartBody.getReduction())
                                                                .build()
                                                )
                                        )
                                )
                                .get(0)
                                .getCart()
                );

    }
}
