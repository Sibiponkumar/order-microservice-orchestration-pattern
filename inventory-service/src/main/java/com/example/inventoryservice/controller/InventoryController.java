package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Order;
import com.example.orderservice.model.InventoryRequest;
import com.example.orderservice.model.InventoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping("/reserve")
    @Operation(summary = "Reserve inventory via WebClient")
    public Order reserveInventory(@RequestBody Order order) {
        boolean available = order.getQuantity() <= 5;
        order.setStatus(available ? "APPROVED" : "REJECTED");
        kafkaTemplate.send("inventory-result", order.getOrderId(), order);
        log.info("WebClient completed for inventory-service");
        return order;
    }

    @PostMapping("/check")
    public InventoryResponse check(@RequestBody InventoryRequest req) {
        return new InventoryResponse(true, 100);
    }
}
