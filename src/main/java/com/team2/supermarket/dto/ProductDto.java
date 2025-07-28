package com.team2.supermarket.dto;
import com.team2.supermarket.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;

    @NotBlank(message = "Category ID is required")
    private String categoryId;








}
