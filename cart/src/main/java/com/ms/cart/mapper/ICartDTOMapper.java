package com.ms.cart.mapper;

import com.ms.cart.dto.CartDTO;
import com.ms.cart.dto.CartItemDTO;
import com.ms.cart.models.Cart;
import com.ms.cart.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICartDTOMapper {
    ICartDTOMapper INSTANCE = Mappers.getMapper( ICartDTOMapper.class );

    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
    List<CartItemDTO> cartItemListToCartItemDTOList(List<CartItem> cartItemList);
    List<CartItem> cartItemDTOListToCartItemList(List<CartItemDTO> cartItemDTOList);
}
