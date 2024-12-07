package com.ambev.order.controller;


import com.ambev.order.dto.OrderRequest;
import com.ambev.order.dto.OrderResponse;
import com.ambev.order.model.OrderStatusEnum;
import com.ambev.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Gestão de pedidos")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido.")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return service.save(orderRequest);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos com ou sem filtro", description = "Retorna a lista dos pedidos.")
    public List<OrderResponse> findOrders(@RequestParam Optional<OrderStatusEnum> status) {
        return service.findOrders(status);
    }
}
