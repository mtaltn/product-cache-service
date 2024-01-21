package com.mta.stockmanagement.productcacheservice.service;

import com.mta.stockmanagement.productcacheservice.enums.Language;
import com.mta.stockmanagement.productcacheservice.repository.entity.Product;
import com.mta.stockmanagement.productcacheservice.response.ProductResponse;

import java.util.List;

public interface ProductRepositoryService {
    Product getbyid(Language language, Long Id);
    void deleteProductsFromCache(Language language);

    ProductResponse convertProductResponse(Product product);
    List<ProductResponse> convertProductResponseList(List<Product> productList);
}
