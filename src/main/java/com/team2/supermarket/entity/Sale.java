package com.team2.supermarket.entity;

import com.team2.supermarket.dto.SaleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "sale")
public class Sale {



    @Id
    private String id;

    private int quantity;

    private double price;

    private LocalDateTime date;

    private String productId;

    public Sale(SaleDto dto) {
        this.quantity = dto.getQuantity();
        this.price = dto.getPrice();
        this.productId = dto.getProductId();
        this.date = LocalDateTime.now();
    }


}
