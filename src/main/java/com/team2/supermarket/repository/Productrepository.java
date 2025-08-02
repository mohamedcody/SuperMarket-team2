package com.team2.supermarket.repository;

import com.team2.supermarket.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Productrepository extends MongoRepository<Product,String > {
    boolean findByName(String name);

}
