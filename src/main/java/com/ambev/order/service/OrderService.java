package com.ambev.order.service;

import com.ambev.order.dto.OrderRequest;
import com.ambev.order.dto.OrderResponse;
import com.ambev.order.model.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    OrderResponse save(OrderRequest orderRequest);

    List<OrderResponse> findOrders (Optional<OrderStatusEnum> status);
}
