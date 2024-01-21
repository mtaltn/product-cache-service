package com.mta.stockmanagement.productcacheservice.repository;

import com.mta.stockmanagement.productcacheservice.repository.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
