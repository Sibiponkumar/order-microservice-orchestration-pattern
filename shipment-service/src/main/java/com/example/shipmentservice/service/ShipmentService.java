/*
package com.example.shipmentservice.service;

import com.example.shipmentservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipmentService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "shipment-group")
    public void scheduleShipment(Order order) {
        log.info("Scheduling shipment for order: {}", order.getOrderId());

        boolean scheduled = Math.random() > 0.1;
        order.setStatus(scheduled ? "APPROVED" : "REJECTED");

        kafkaTemplate.send("shipment-result", order.getOrderId(), order);
        log.info("Shipment {} for order: {}", order.getStatus(), order.getOrderId());
    }
}
*/
