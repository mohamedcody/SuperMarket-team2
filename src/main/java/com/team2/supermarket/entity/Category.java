package com.team2.supermarket.entity;
import com.team2.supermarket.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {


    private String name;

    public Category(CategoryDto dto) {
        this.setId(dto.getId());
        this.setCreateBy(dto.getCreateBy());
        this.setUpdateBy(dto.getUpdateBy());
        this.setCreateAt(dto.getCreateAt());
        this.setUpdateAt(dto.getUpdateAt());

        this.name = dto.getName();
    }


}

