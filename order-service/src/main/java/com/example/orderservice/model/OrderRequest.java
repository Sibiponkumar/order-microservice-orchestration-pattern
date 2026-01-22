package com.example.orderservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderRequest(
        @Schema(description = "Product ID") String productId,
        @Schema(description = "Quantity") Integer quantity,
        @Schema(description = "User ID") String userId) {}
