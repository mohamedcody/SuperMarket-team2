package com.team2.supermarket.controller;
import com.team2.supermarket.dto.ProductDto;
import com.team2.supermarket.service.Implementation.ProductServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceImp productService;

    @PostMapping
    public String createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable String id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.delete(id);
    }



}
