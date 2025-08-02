package com.team2.supermarket.entity;
import com.team2.supermarket.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

   private String name;
   private String description;
   private Integer stock;
   private Double price;

   private String categoryId;
   private String categoryName;

   public Product(ProductDto dto){
      this.setId(dto.getId());
      this.setCreateBy(dto.getCreateBy());
      this.setUpdateBy(dto.getUpdateBy());
      this.setCreateAt(dto.getCreateAt());
      this.setUpdateAt(dto.getUpdateAt());

      this.name = dto.getName();
      this.description = dto.getDescription();
      this.stock = dto.getStock();
      this.price = dto.getPrice();

      if (dto.getCategory() != null) {
         this.categoryId = dto.getCategory().getId();
         this.categoryName = dto.getCategory().getName();
      }
   }


}

