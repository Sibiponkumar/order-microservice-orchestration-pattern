package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@Slf4j
public class OrderServiceKafkaConsumer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final WebClient inventoryWebClient;
    private final WebClient paymentWebClient;
    private final WebClient shipmentWebClient;

    public OrderServiceKafkaConsumer(
            KafkaTemplate<String, Order> kafkaTemplate,
            @Qualifier("inventoryWebClient") WebClient inventoryWebClient,
            @Qualifier("paymentWebClient") WebClient paymentWebClient,
            @Qualifier("shipmentWebClient") WebClient shipmentWebClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryWebClient = inventoryWebClient;
        this.paymentWebClient = paymentWebClient;
        this.shipmentWebClient = shipmentWebClient;
    }

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void startSaga(Order order) {
        Mono<Order> inventoryCheck = checkInventory(order);
        Mono<Order> paymentCheck = checkPayment(order);
        Mono<Order> shipmentCheck = checkShipment(order);

        Mono.zip(inventoryCheck, paymentCheck, shipmentCheck)
                .doOnSuccess(results -> {
                    String traceId = java.util
                            .UUID
                            .randomUUID()
                            .toString()
                            .replaceAll("-", "")
                            .substring(0, 16);
                    MDC.put("traceId", traceId);
                    MDC.put("spanId", "kafka-" + order.getOrderId());

                    Order inventoryResult = results.getT1();
                    Order paymentResult = results.getT2();
                    Order shipmentResult = results.getT3();

                    if (isAnyRejected(inventoryResult, paymentResult, shipmentResult)) {
                        order.setStatus("FAILED");
                    } else {
                        order.setStatus("COMPLETED");
                    }

                    kafkaTemplate.send("order-outcome", order.getOrderId(), order);
                    log.info(
                            "WebClient completed for {}: {} [traceId={}]",
                            order.getOrderId(),
                            order.getStatus(),
                            traceId);

                    MDC.clear();
                })
                .doOnError(error -> {
                    log.error("Order failed for {} ", order.getOrderId(), error);
                    MDC.clear();
                })
                .subscribe();
    }

    private Mono<Order> checkInventory(Order order) {
        return inventoryWebClient
                .post()
                .uri("http://localhost:8081/api/inventory/reserve")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Order.class)
                .doOnNext(result -> log.info("Inventory response: {} for {}", result.getStatus(), order.getOrderId()))
                .onErrorReturn(createFailedOrder(order, "Inventory"));
    }

    private Mono<Order> checkPayment(Order order) {
        return paymentWebClient
                .post()
                .uri("http://localhost:8082/api/payment/process")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Order.class)
                .doOnNext(result -> log.info("Payment response: {} for {}", result.getStatus(), order.getOrderId()))
                .onErrorReturn(createFailedOrder(order, "Payment"));
    }

    private Mono<Order> checkShipment(Order order) {
        return shipmentWebClient
                .post()
                .uri("http://localhost:8083/api/shipment/schedule")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Order.class)
                .doOnNext(result -> log.info("Shipment response: {} for {}", result.getStatus(), order.getOrderId()))
                .onErrorReturn(createFailedOrder(order, "Shipment"));
    }

    private Order createFailedOrder(Order order, String service) {
        Order failed = new Order();
        failed.setOrderId(order.getOrderId());
        failed.setStatus("REJECTED - " + service);
        return failed;
    }

    private boolean isAnyRejected(Order... results) {
        return Arrays.stream(results).anyMatch(result -> "REJECTED".equals(result.getStatus()));
    }
}
