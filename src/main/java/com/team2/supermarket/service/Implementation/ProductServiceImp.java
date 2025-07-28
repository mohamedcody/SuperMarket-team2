package com.team2.supermarket.service.Implementation;

import com.team2.supermarket.dto.ProductDto;
import com.team2.supermarket.entity.Product;
import com.team2.supermarket.exception.NotFoundException;
import com.team2.supermarket.mapper.ProductMapper;
import com.team2.supermarket.repository.Productrepository;
import com.team2.supermarket.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProductServiceImp implements CrudService<ProductDto, String> {

    @Autowired
    private Productrepository repo;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private MessageSource messageSource;

    private final Locale locale = new Locale("ar");

    @Override
    public String create(ProductDto productDto) {
        boolean exists = repo.findAll().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(productDto.getName()));

        if (exists) {
            String msg = messageSource.getMessage("product.exists", new Object[]{productDto.getName()}, locale);
            throw new NotFoundException(msg);
        }

        Product product = mapper.toEntity(productDto);
        repo.save(product);
        return product.getId();
    }

    @Override
    public ProductDto getById(String id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> {
                    String msg = messageSource.getMessage("product.not_found", new Object[]{id}, locale);
                    return new NotFoundException(msg);
                });

        return mapper.toDto(product);
    }

    @Override
    public String update(String id, ProductDto productDto) {
        Product product = repo.findById(id)
                .orElseThrow(() -> {
                    String msg = messageSource.getMessage("product.not_found", new Object[]{id}, locale);
                    return new NotFoundException(msg);
                });

        product.setName(productDto.getName());
        repo.save(product);

        return messageSource.getMessage("product.updated", new Object[]{id}, locale);
    }

    @Override
    public void delete(String id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> {
                    String msg = messageSource.getMessage("product.not_found", new Object[]{id}, locale);
                    return new NotFoundException(msg);
                });

        repo.delete(product);
    }
}
