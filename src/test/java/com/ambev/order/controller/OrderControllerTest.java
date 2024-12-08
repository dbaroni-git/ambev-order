package com.ambev.order.controller;

import com.ambev.order.dto.OrderProductRequest;
import com.ambev.order.dto.OrderRequest;
import com.ambev.order.model.OrderStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder200() throws Exception {

        OrderRequest orderRequest = new OrderRequest();

        OrderProductRequest orderProductRequest1 = new OrderProductRequest();
        orderProductRequest1.setProductName("PRODUTO1");
        orderProductRequest1.setPrice(BigDecimal.valueOf(2.59));
        orderProductRequest1.setQuantity(3);

        OrderProductRequest orderProductRequest2 = new OrderProductRequest();
        orderProductRequest2.setProductName("PRODUTO2");
        orderProductRequest2.setPrice(BigDecimal.valueOf(9.15));
        orderProductRequest2.setQuantity(8);

        orderRequest.setTransactionId("TX001");
        orderRequest.setProducts(Arrays.asList(orderProductRequest1, orderProductRequest2));


        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(OrderStatusEnum.PENDING.toString()))
                .andExpect(jsonPath("$.transactionId").value(orderRequest.getTransactionId()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.products").isNotEmpty())
                .andExpect(jsonPath("$.totalValue").value(80.97));

    }

    @Test
    void testCreateOrder400() throws Exception {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setTransactionId("TX001");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testFindOrders() throws Exception {

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
