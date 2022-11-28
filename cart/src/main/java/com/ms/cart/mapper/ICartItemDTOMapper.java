package com.ms.cart.mapper;

import com.ms.cart.dto.CartItemDTO;
import com.ms.cart.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICartItemDTOMapper {
    ICartItemDTOMapper INSTANCE = Mappers.getMapper( ICartItemDTOMapper.class );

    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
    CartItem cartItemDTOToCartItem(CartItemDTO CartItemDTO);
    List<CartItemDTO> cartItemListToCartItemDTOList(List<CartItem> CartItemList);
    List<CartItem> cartItemDTOListToCartItemList(List<CartItemDTO> CartItemDTOList);

}
