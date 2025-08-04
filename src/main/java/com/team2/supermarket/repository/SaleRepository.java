package com.team2.supermarket.repository;

import com.team2.supermarket.entity.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends MongoRepository<Sale,String> {

}
