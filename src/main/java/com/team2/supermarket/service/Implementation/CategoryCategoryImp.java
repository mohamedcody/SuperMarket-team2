package com.team2.supermarket.service.Implementation;
import com.team2.supermarket.dto.CategoryDto;
import com.team2.supermarket.dto.PageResult;
import com.team2.supermarket.dto.ProductDto;
import com.team2.supermarket.entity.Category;

import com.team2.supermarket.entity.Product;
import com.team2.supermarket.exception.NotFoundException;
import com.team2.supermarket.repository.CategoryRepository;
import com.team2.supermarket.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryCategoryImp implements CrudService<CategoryDto, String> {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MongoTemplate mongoTemplate;

    private Locale locale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String create(CategoryDto dto) {
        List<Category> all = categoryRepository.findAll();
        boolean exists = all.stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(dto.getName()));

        if (exists) {
            throw new NotFoundException(messageSource.getMessage(
                    "category.exists", new Object[]{dto.getName()}, locale()));
        }

        Category category = new Category();
        category.setName(dto.getName());

        Category saved = categoryRepository.save(category);
        return saved.getId();
    }

    @Override
    public CategoryDto getById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("category.not.found.id", new Object[]{id}, locale())));

        return new CategoryDto(category);
    }



    @Override
    public String update(String id , CategoryDto dto) {


        Category oldCategory = mongoTemplate.findById(id, Category.class);
        if (oldCategory == null) {
            throw new NotFoundException(
                    messageSource.getMessage("category.not.found.id", new Object[]{id}, locale()));
        }


        oldCategory.setName(dto.getName());
        oldCategory.setUpdateBy(dto.getUpdateBy());
        oldCategory.setUpdateAt(dto.getUpdateAt());


        mongoTemplate.save(oldCategory);


        return messageSource.getMessage("category.updated", new Object[]{id}, locale());

    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("category.not.found.id", new Object[]{id}, locale())));

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult search(Map<String, String> params) {
        System.err.println(params);

        Integer page = Integer.valueOf(params.getOrDefault("page", "0"));
        Integer size = Integer.valueOf(params.getOrDefault("size", "15"));


        Criteria criteria = criteriaBuilder(params);


        MatchOperation match = Aggregation.match(criteria);


        LookupOperation lookupCategory = LookupOperation.newLookup()
                .from("category")
                .localField("categoryId")
                .foreignField("_id")
                .as("category");


        SkipOperation skip = Aggregation.skip((long) page * size);
        LimitOperation limit = Aggregation.limit(size);


        Aggregation aggregation = Aggregation.newAggregation(
                match,
                lookupCategory,
                skip,
                limit
        );

        AggregationResults<ProductDto> results = mongoTemplate.aggregate(
                aggregation,
                "product",
                ProductDto.class
        );


        List<ProductDto> products = results.getMappedResults();
        long totalCount = mongoTemplate.count(Query.query(criteria), Product.class);

        return new PageResult(products, totalCount);
    }

    private Criteria criteriaBuilder(Map<String,String> params ) {
        params.remove("page");
        params.remove("size");

        List<Criteria> criterias = new ArrayList<>();

        for (String key : params.keySet()) {
            criterias.add(Criteria.where(key).regex(params.get(key), "i"));
        }

        return new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }




}