package com.ambev.order.validation;


import com.ambev.order.repository.OrderRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionIdValidator implements ConstraintValidator<UniqueTransactionId, String> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void initialize(UniqueTransactionId constraintAnnotation) {

    }

    @Override
    public boolean isValid(String transactionId, ConstraintValidatorContext constraintValidatorContext) {
        return !orderRepository.existsByTransactionId(transactionId);
    }

}
