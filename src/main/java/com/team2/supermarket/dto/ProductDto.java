package com.team2.supermarket.dto;
import com.team2.supermarket.entity.BaseEntity;
import com.team2.supermarket.entity.Category;
import com.team2.supermarket.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto extends BaseEntity {

    @NotBlank(message = "name is mandatory")
    private String name;
    private String description;
    @Min(value = 0 , message = "stock cant be less than 0 ")
    private Integer stock;
    @DecimalMin(value = "0.0",message = "price cant be less than 0 ")
    private Double price;

    private Category category;

    public ProductDto(Product entity) {
        this.setId(entity.getId());
        this.setCreateBy(entity.getCreateBy());
        this.setUpdateBy(entity.getUpdateBy());
        this.setCreateAt(entity.getCreateAt());
        this.setUpdateAt(entity.getUpdateAt());

        this.name = entity.getName();
        this.description = entity.getDescription();
        this.stock = entity.getStock();
        this.price = entity.getPrice();

        if (entity.getCategoryId() != null && entity.getCategoryName() != null) {
            this.category = new Category();
            this.category.setId(entity.getCategoryId());
            this.category.setName(entity.getCategoryName());
        }
    }
}