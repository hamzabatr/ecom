package com.ms.product.mapper;

import com.ms.product.dto.CommentDTO;
import com.ms.product.dto.ProductDTO;
import com.ms.product.entities.Comment;
import com.ms.product.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductDTOMapper {
    IProductDTOMapper INSTANCE = Mappers.getMapper( IProductDTOMapper.class );

    @Mapping(target = "comments", source = "comments", qualifiedByName = "mapComments")
    ProductDTO productToProductDTO(Product product);

    @Mapping(target = "likes", source = "likes")
    @Mapping(target = "dislikes", source = "dislikes")
    Product productDTOToProduct(ProductDTO productDTO);
    List<ProductDTO> productListToProductDTOList(List<Product> productList);

    @Named("mapComments")
    default List<CommentDTO> mapComments(List<Comment> comments) {
        return ICommentDTOMapper.INSTANCE.commentListToCommentDTOList(comments);
    }

//    @Named("mapLikesOrDislikes")
//    default long mapLikesOrDislikes(Long likesOrDislikes) {
//        return likesOrDislikes != null ? likesOrDislikes : 0;
//    }
}
