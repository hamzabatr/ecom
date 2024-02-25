package com.ms.product.dto.body;

import lombok.Getter;

@Getter
public class CommentBody {
    private Long productId;
    private String userId;
    private Integer note;
    private String comment;
}
