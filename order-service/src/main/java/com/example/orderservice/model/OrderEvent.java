package com.example.orderservice.model;

public record OrderEvent(
        String orderId, String productId, Integer quantity, Double amount, String userId, String status) {}
