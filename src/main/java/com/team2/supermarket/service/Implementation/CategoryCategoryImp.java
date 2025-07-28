package com.team2.supermarket.service.Implementation;

import com.team2.supermarket.dto.CategoryDto;
import com.team2.supermarket.entity.Category;
import com.team2.supermarket.exception.NotFoundException;
import com.team2.supermarket.mapper.CategoryMapper;
import com.team2.supermarket.repository.CategoryRepository;
import com.team2.supermarket.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoryCategoryImp implements CrudService<CategoryDto, String> {

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private CategoryMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public String create(CategoryDto categoryDto) {
        boolean exists = repo.findAll().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(categoryDto.getName()));

        if (exists) {
            String message = messageSource.getMessage("category.exists", new Object[]{categoryDto.getName()}, LocaleContextHolder.getLocale());
            throw new NotFoundException(message);
        }

        Category category = mapper.toEntity(categoryDto);
        repo.save(category);
        return category.getId();
    }

    @Override
    public CategoryDto getById(String id) {
        Category category = repo.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("category.not.found.id", new Object[]{id}, LocaleContextHolder.getLocale());
                    return new NotFoundException(message);
                });

        return mapper.toDto(category);
    }

    @Override
    public String update(String id, CategoryDto categoryDto) {
        Category category = repo.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("category.not.found.id", new Object[]{id}, LocaleContextHolder.getLocale());
                    return new NotFoundException(message);
                });

        category.setName(categoryDto.getName());
        repo.save(category);

        return messageSource.getMessage("category.updated", new Object[]{id}, LocaleContextHolder.getLocale());
    }

    @Override
    public void delete(String id) {
        Category category = repo.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("category.not.found.id", new Object[]{id}, LocaleContextHolder.getLocale());
                    throw new NotFoundException(message);
                });

        repo.delete(category);
    }

}
