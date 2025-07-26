package com.team2.supermaket.mapper;

import com.team2.supermaket.dto.ProductDto;
import com.team2.supermaket.entity.Product;


public class ProductMapper {

    public static ProductDto toDto(Product product , String categoryName){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)
                .build();
    }
    public static Product toEntity (ProductDto dto ){
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .categoryId(dto.getCategoryId())
//
                .build();
    }

}
