package com.team2.supermarket.controller;
import com.team2.supermarket.dto.PageResult;
import com.team2.supermarket.dto.ProductDto;
import com.team2.supermarket.entity.Product;
import com.team2.supermarket.service.CrudService;
import com.team2.supermarket.service.Implementation.ProductServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private CrudService<ProductDto, String> productService;

    @PostMapping
    public String create(@RequestBody ProductDto dto) {
        return productService.create(dto);
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable String id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody ProductDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/search")
    public PageResult search(@RequestParam Map<String, String> params) {
        return productService.search(params);
    }




}
