package com.ambev.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderProdutctResponse {

    private Long id;

    private String productName;

    private Integer quantity;

    private BigDecimal price;
}
