package com.example.orderservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderResponse(
        @Schema(description = "Order ID") String orderId,
        @Schema(description = "Message") String message) {}
