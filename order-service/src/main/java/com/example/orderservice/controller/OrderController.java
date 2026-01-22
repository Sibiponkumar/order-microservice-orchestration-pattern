package com.example.orderservice.controller;

import com.example.orderservice.constants.Constants;
import com.example.orderservice.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Service API", description = "Order Orchestration")
@RequiredArgsConstructor
public class OrderController {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping
    @Operation(summary = "Create new order")
    @ApiResponse(responseCode = "202", description = "Order accepted for processing")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        order.setStatus("CREATED");
        kafkaTemplate.send(Constants.ORDER_CREATED_TOPIC, order.getOrderId(), order);
        return ResponseEntity.accepted().body("Order accepted: " + order.getOrderId());
    }
}
