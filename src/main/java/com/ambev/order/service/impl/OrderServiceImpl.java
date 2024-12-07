package com.ambev.order.service.impl;

import com.ambev.order.dto.OrderProductRequest;
import com.ambev.order.dto.OrderProdutctResponse;
import com.ambev.order.dto.OrderRequest;
import com.ambev.order.dto.OrderResponse;
import com.ambev.order.model.Order;
import com.ambev.order.model.OrderProduct;
import com.ambev.order.model.OrderStatusEnum;
import com.ambev.order.repository.OrderRepository;
import com.ambev.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }


    @Override
    public OrderResponse save(OrderRequest orderRequest) {

        BigDecimal total = orderRequest.getProducts().stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order = Order.builder()
                .totalValue(total)
                .status(OrderStatusEnum.PENDING)
                .transactionId(orderRequest.getTransactionId())
                .build();

        order.setOrderProducts(convertToOrderProduct(order, orderRequest.getProducts()));

        order = repository.save(order);
        return buildOrderResponse(order);
    }

    @Override
    public List<OrderResponse> findOrders(Optional<OrderStatusEnum> status) {

        List<Order> orders = status.isPresent() ? repository.findByStatus(status.get()) : repository.findAll();

        return orders.stream().map(this::buildOrderResponse).collect(Collectors.toList());
    }

    private List<OrderProduct> convertToOrderProduct(Order order, List<OrderProductRequest> products) {
        return products.stream().map(orderProductRequest -> OrderProduct.builder()
                        .productName(orderProductRequest.getProductName())
                        .quantity(orderProductRequest.getQuantity())
                        .price(orderProductRequest.getPrice()).order(order)
                        .build())
                .collect(Collectors.toList());
    }

    private List<OrderProdutctResponse> convertToOrderProductResponse(List<OrderProduct> products) {
        return products.stream().map(orderProduct ->
                        OrderProdutctResponse.builder()
                                .id(orderProduct.getId())
                                .productName(orderProduct.getProductName())
                                .quantity(orderProduct.getQuantity())
                                .price(orderProduct.getPrice())
                                .build())
                .collect(Collectors.toList());
    }

    private OrderResponse buildOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalValue(order.getTotalValue())
                .transactionId(order.getTransactionId())
                .status(order.getStatus())
                .products(convertToOrderProductResponse(order.getOrderProducts()))
                .build();
    }
}
