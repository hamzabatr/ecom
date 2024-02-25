package com.ms.product.mapper;

import com.ms.product.dto.CommentDTO;
import com.ms.product.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICommentDTOMapper {
    ICommentDTOMapper INSTANCE = Mappers.getMapper( ICommentDTOMapper.class );

    @Mapping(target = "productId", source = "product.id")
    CommentDTO commentToCommentDTO(Comment comment);

    List<CommentDTO> commentListToCommentDTOList(List<Comment> comments);
}
