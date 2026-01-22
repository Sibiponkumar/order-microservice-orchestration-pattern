package com.example.orderservice.consumer;

import com.example.orderservice.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventConsumer {
    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consume(OrderEvent event) {
        log.info("Consumed: {} - {}", event.orderId(), event.status());
    }
}
