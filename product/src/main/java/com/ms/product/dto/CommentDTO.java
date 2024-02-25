package com.ms.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private long id;
    private long productId;
    private String userId;
    private String comment;
    private int note;
    private long likes;
    private long dislikes;
}
