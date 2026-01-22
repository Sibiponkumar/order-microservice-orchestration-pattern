package com.example.shipmentservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order Event for Saga Pattern")
public class Order {
    @Schema(description = "Unique order identifier")
    private String orderId;

    @Schema(description = "Customer identifier")
    private String customerId;

    @Schema(description = "Product identifier")
    private String productId;

    @Schema(description = "Order quantity")
    private int quantity;

    @Schema(description = "Order amount")
    private double amount;

    @Schema(description = "Current status: PENDING, CREATED, APPROVED, REJECTED, COMPLETED, FAILED")
    private String status = "PENDING";
}
