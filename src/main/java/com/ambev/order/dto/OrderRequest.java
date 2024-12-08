package com.ambev.order.dto;

import com.ambev.order.validation.UniqueTransactionId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotBlank(message = "O transactionId não pode estar vazio")
    @UniqueTransactionId
    private String transactionId;

    @Valid
    @Size(min = 1, message = "A lista de produtos não pode estar vazia")
    @NotNull(message = "A lista de produtos não pode estar nula")
    private List<OrderProductRequest> products;
}
