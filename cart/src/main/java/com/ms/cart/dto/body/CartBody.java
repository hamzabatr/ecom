package com.ms.cart.dto.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.cart.dto.ItemDTO;
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
public class CartBody {
    @JsonProperty(required = true)
    private long cartId;

    @JsonProperty(required = true)
    private String userId;

    @JsonProperty(required = true)
    private List<ItemDTO> items;

    private BigDecimal reduction;
}
