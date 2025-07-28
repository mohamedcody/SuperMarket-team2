package com.team2.supermarket.repository;

import com.team2.supermarket.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Productrepository extends MongoRepository<Product,String > {
}
