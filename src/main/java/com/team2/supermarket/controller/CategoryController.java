package com.team2.supermarket.controller;


import com.team2.supermarket.dto.CategoryDto;
import com.team2.supermarket.service.Implementation.CategoryCategoryImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {



    private final CategoryCategoryImp categoryService;

    @PostMapping
    public String createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable String id, @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.delete(id);
    }
}
