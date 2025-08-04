package com.team2.supermarket.dto;

import com.team2.supermarket.entity.Sale;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SaleDto {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Min(value = 0, message = "Price must be 0 or more")
    private double price;

    public SaleDto(Sale sale) {
        this.productId = sale.getProductId();
        this.quantity = sale.getQuantity();
        this.price = sale.getPrice();
    }
}
