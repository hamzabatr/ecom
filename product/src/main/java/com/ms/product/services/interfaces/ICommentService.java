package com.ms.product.services.interfaces;

import com.ms.product.dto.CommentDTO;
import com.ms.product.dto.body.CommentBody;

public interface ICommentService {
    CommentDTO postCommentToProduct (CommentBody commentBody);
    CommentDTO likeById (Long id, Boolean likeBool);
    CommentDTO dislikeById (Long id, Boolean dislikeBool);
}
