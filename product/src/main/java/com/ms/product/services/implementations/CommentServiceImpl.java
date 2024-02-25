package com.ms.product.services.implementations;

import com.ms.product.dto.CommentDTO;
import com.ms.product.dto.UserDTO;
import com.ms.product.dto.body.CommentBody;
import com.ms.product.entities.Comment;
import com.ms.product.entities.Product;
import com.ms.product.feign.IUserFeignClient;
import com.ms.product.mapper.ICommentDTOMapper;
import com.ms.product.repositories.ICommentRepository;
import com.ms.product.repositories.IProductRepository;
import com.ms.product.services.interfaces.ICommentService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final ICommentDTOMapper commentDTOMapper;
    private final IProductRepository productRepository;
    private final IUserFeignClient userFeignClient;

    @Override
    @Transactional
    public CommentDTO postCommentToProduct(CommentBody commentBody) {
        if (
                (commentBody.getProductId() < 1) ||
                        !StringUtils.hasText(commentBody.getUserId()) ||
                        !(0 <= commentBody.getNote() && commentBody.getNote() <= 5)
        ) {
            throw new IllegalArgumentException();
        }

        Optional<UserDTO> userDTO = Optional.ofNullable(userFeignClient.getUserById(commentBody.getUserId()).getBody());
        Optional<Product> product = productRepository.findById(commentBody.getProductId());

        if (userDTO.isPresent() && product.isPresent()) {
            return commentDTOMapper.commentToCommentDTO(
                    commentRepository.save(
                            Comment.builder()
                                    .product(product.get())
                                    .userId(commentBody.getUserId())
                                    .comment(commentBody.getComment())
                                    .note(commentBody.getNote())
                                    .build()
                    )
            );
        }

        throw new InternalException("Internal error.");

    }

    @Override
    @Transactional
    public CommentDTO likeById(Long id, Boolean likeBool) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the comment with id = " + id));

        comment.setLikes(handleLikeOrDislike(comment.getLikes() != null ? comment.getLikes() : 0, likeBool));

        return commentDTOMapper.commentToCommentDTO(commentRepository.save(comment));
    }


    @Override
    @Transactional
    public CommentDTO dislikeById(Long id, Boolean dislikeBool) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the comment with id = " + id));
        comment.setDislikes(handleLikeOrDislike(comment.getDislikes() != null ? comment.getDislikes() : 0, dislikeBool));

        return commentDTOMapper.commentToCommentDTO(commentRepository.save(comment));
    }

    private Long handleLikeOrDislike(long likesOrDislikes, boolean likeOrDislikeBool) {
        return likeOrDislikeBool ? likesOrDislikes + 1 : (likesOrDislikes > 0 ? likesOrDislikes - 1 : 0);
    }

}
