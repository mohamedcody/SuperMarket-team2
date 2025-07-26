package com.team2.supermaket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryId;
    private String categoryName;


}
