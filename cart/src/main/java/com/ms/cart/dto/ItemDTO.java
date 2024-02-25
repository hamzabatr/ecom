package com.ms.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private long id;

    @JsonProperty(required = true)
    private long productId;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private long quantity;

    @JsonProperty(required = true)
    private BigDecimal price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal reduction;
}
