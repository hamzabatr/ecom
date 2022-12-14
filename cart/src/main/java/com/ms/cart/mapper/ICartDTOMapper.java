package com.ms.cart.mapper;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICartDTOMapper {
    ICartDTOMapper INSTANCE = Mappers.getMapper( ICartDTOMapper.class );

    CartDTO cartToCartDTO(Cart Cart);
    Cart cartDTOToCart(CartDTO CartDTO);
    List<CartDTO> cartListToCartDTOList(List<Cart> CartList);
    List<Cart> cartDTOListToCartList(List<CartDTO> CartDTOList);
}
