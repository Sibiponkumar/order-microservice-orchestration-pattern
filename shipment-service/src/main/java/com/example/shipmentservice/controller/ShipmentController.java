package com.example.shipmentservice.controller;

import com.example.shipmentservice.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping("/schedule")
    @Operation(summary = "Schedule shipment via WebClient")
    public Order scheduleShipment(@RequestBody Order order) {
        boolean scheduled = Math.random() > 0.1;
        order.setStatus(scheduled ? "APPROVED" : "REJECTED");

        kafkaTemplate.send("shipment-result", order.getOrderId(), order);
        return order;
    }
}
