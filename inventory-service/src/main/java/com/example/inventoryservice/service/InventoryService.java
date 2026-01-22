/*
package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void reserveInventory(Order order) {
        log.info("Checking inventory for order: {}", order.getOrderId());

        boolean available = order.getQuantity() <= 5;
        order.setStatus(available ? "APPROVED" : "REJECTED");

        kafkaTemplate.send("inventory-result", order.getOrderId(), order);
        log.info("Inventory {} for order: {}", order.getStatus(), order.getOrderId());
    }
}
*/
