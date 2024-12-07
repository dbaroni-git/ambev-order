package com.ambev.order.dto;

import com.ambev.order.model.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResponse {

    private UUID id;

    private BigDecimal totalValue;

    private List<OrderProdutctResponse> products;

    private String transactionId;

    private OrderStatusEnum status;
}
