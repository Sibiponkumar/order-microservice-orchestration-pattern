package com.example.orderservice.model;

public record PaymentRequest(String orderId, String userId, Double amount) {}
