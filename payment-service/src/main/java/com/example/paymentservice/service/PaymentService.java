/*
package com.example.paymentservice.service;

import com.example.paymentservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void processPayment(Order order) {
        log.info("Processing payment for order: {}", order.getOrderId());

        boolean paid = order.getAmount() <= 1000;
        order.setStatus(paid ? "APPROVED" : "REJECTED");

        kafkaTemplate.send("payment-result", order.getOrderId(), order);
        log.info("Payment {} for order: {}", order.getStatus(), order.getOrderId());
    }
}
*/
