package com.team2.supermaket.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryId;

}
