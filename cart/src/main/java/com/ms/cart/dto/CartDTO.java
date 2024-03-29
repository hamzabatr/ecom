package com.ms.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private long id;
    private String userId;
    private List<ItemDTO> items;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal reduction;
}