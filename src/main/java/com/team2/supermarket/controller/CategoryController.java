package com.team2.supermarket.controller;
import com.team2.supermarket.dto.CategoryDto;
import com.team2.supermarket.dto.PageResult;
import com.team2.supermarket.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CrudService<CategoryDto, String> categoryService;

    @PostMapping
    public String create(@RequestBody CategoryDto dto) {
        return categoryService.create(dto);
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody CategoryDto dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/search")
    public PageResult search(@RequestParam Map<String, String> params) {
        return categoryService.search(params);
    }





}
