package com.ms.cart.mapper;

import com.ms.cart.dto.ItemDTO;
import com.ms.cart.models.Cart;
import com.ms.cart.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IItemDTOMapper {
    IItemDTOMapper INSTANCE = Mappers.getMapper( IItemDTOMapper.class );

    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "id", source = "itemDTO.id")
    @Mapping(target = "reduction", source = "itemDTO.reduction")
    Item itemDTOToItem(ItemDTO itemDTO, Cart cart);

//    List<Item> itemsDTOToItems(List<ItemDTO> itemsDTO, Cart cart);
    default List<Item> itemsDTOToItems(List<ItemDTO> itemsDTO, Cart cart){
        return itemsDTO.stream()
                .map(item -> itemDTOToItem(item, cart))
                .collect(Collectors.toList());
    }
}
