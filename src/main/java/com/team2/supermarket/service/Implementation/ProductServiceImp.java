package com.team2.supermarket.service.Implementation;
import com.team2.supermarket.dto.PageResult;
import com.team2.supermarket.dto.ProductDto;
import com.team2.supermarket.entity.Category;
import com.team2.supermarket.entity.Product;
import com.team2.supermarket.exception.NotFoundException;
import com.team2.supermarket.repository.CategoryRepository;
import com.team2.supermarket.repository.Productrepository;
import com.team2.supermarket.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImp implements CrudService<ProductDto,String> {

    @Autowired
    private Productrepository productRepository;

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
    public String create(ProductDto dto) {
        boolean exists = productRepository.findByName(dto.getName());
        if (exists) {
            throw new NotFoundException(
                    messageSource.getMessage("product.exists", new Object[]{dto.getName()}, locale())
            );
        }

        Category category = Optional.ofNullable(dto.getCategory())
                .map(c -> categoryRepository.findById(c.getId())
                        .orElseThrow(() -> new NotFoundException(
                                messageSource.getMessage("category.not.found.id", new Object[]{c.getId()}, locale())))
                ).orElse(null);

        Product product = new Product(dto);
        Product saved = productRepository.save(product);
        return saved.getId();
    }

    @Override
    public ProductDto getById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("product.not_found", new Object[]{id}, locale()))
        );
        return new ProductDto(product);
    }

    @Override
    public String update(String id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("product.not_found", new Object[]{id}, locale()))
        );

        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException(
                            messageSource.getMessage("category.not.found.id", new Object[]{dto.getCategory().getId()}, locale())
                    ));
            product.setCategoryId(category.getId());
            product.setCategoryName(category.getName());
        }

        Product updated = productRepository.save(product);
        return messageSource.getMessage("product.updated", new Object[]{updated.getId()}, locale());
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("product.not_found", new Object[]{id}, locale()))
        );
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult search(Map<String, String> params) {
        System.err.println(params); // للمساعدة في الـ debugging

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

    private Criteria criteriaBuilder(Map<String, String> params) {
        params.remove("page");
        params.remove("size");

        List<Criteria> criterias = new ArrayList<>();

        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                criterias.add(Criteria.where(key).regex(value, "i"));
            }
        }

        if (criterias.isEmpty()) {
            return new Criteria();
        }

        return new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }


}
