package com.team2.supermaket.mapper;


import com.team2.supermaket.dto.CategoryDto;
import com.team2.supermaket.entity.Category;

public class CategoryMapper {

    //ميثود لتحويل كائن من النوع Category (اللي جاي من قاعدة البيانات) إلى كائن من النوع CategoryDto (اللي هنستخدمه في الـ APi
    public static CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description((category.getDescription()))
                .build();
    }
       //ميثود لتحويل كائن من النوع CategoryDto إلى كائن من النوع Category (علشان نستخدمه مثلاً في حفظ البيانات في قاعدة البيانا
    public static Category toEntity (CategoryDto dto ){
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
