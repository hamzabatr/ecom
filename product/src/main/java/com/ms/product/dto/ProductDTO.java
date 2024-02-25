package com.ms.product.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String illustration;
    private Float price;
    private String userId;
    private List<CommentDTO> comments = new ArrayList<>();
    private Long likes;
    private Long dislikes;
}

