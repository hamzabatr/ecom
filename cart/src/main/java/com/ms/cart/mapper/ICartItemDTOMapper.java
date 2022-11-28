package com.ms.cart.mapper;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICartItemDTOMapper {
    ICartItemDTOMapper INSTANCE = Mappers.getMapper( ICartItemDTOMapper.class );

    CartDTO cartToCartDTO(Cart cart);
    Cart cartDTOToCart(CartDTO cartDTO);
    List<CartDTO> cartListToCartDTOList(List<Cart> cartList);
    List<Cart> cartDTOListToCartList(List<CartDTO> cartDTOList);

}
