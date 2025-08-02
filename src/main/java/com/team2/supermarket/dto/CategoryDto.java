package com.team2.supermarket.dto;

import com.team2.supermarket.entity.BaseEntity;
import com.team2.supermarket.entity.Category;
import com.team2.supermarket.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto  extends BaseEntity {


    @NotBlank(message = "Category name must not be blank")
    private String name;


    public CategoryDto(Category entity) {
        this.setId(entity.getId());
        this.setCreateBy(entity.getCreateBy());
        this.setUpdateBy(entity.getUpdateBy());
        this.setCreateAt(entity.getCreateAt());
        this.setUpdateAt(entity.getUpdateAt());

        this.name = entity.getName();
    }


}

