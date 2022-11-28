package com.ms.product.mapper;

import com.ms.product.dto.ProductDTO;
import com.ms.product.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductDTOMapper {
    IProductDTOMapper INSTANCE = Mappers.getMapper( IProductDTOMapper.class );

    ProductDTO productToProductDTO(Product product);
    Product productDTOToProduct(ProductDTO productDTO);
    List<ProductDTO> productListToProductDTOList(List<Product> productList);
}
