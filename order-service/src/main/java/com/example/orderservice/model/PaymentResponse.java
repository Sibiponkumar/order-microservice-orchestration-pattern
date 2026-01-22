package com.example.orderservice.model;

public record PaymentResponse(boolean success, String transactionId, Double amount) {}
