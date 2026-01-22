package com.example.paymentservice.controller;

import com.example.paymentservice.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping("/process")
    @Operation(summary = "Process payment via WebClient")
    public Order processPayment(@RequestBody Order order) {
        boolean paid = order.getAmount() <= 1000;
        order.setStatus(paid ? "APPROVED" : "REJECTED");

        kafkaTemplate.send("payment-result", order.getOrderId(), order);
        return order;
    }
}
