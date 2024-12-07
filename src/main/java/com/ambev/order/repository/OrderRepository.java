package com.ambev.order.repository;

import com.ambev.order.model.Order;
import com.ambev.order.model.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByTransactionId(String transactionId);
    List<Order> findByStatus(OrderStatusEnum status);
}
