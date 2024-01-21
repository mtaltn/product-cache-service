package com.mta.stockmanagement.productcacheservice.service;

import com.mta.stockmanagement.productcacheservice.enums.Language;
import com.mta.stockmanagement.productcacheservice.repository.entity.Product;

public interface ProductRepositoryService {
    Product getbyid(Language language, Long productId);
    void deleteProductsFromCache(Language language);
}
