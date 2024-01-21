package com.mta.stockmanagement.productcacheservice.service.impl;

import com.mta.stockmanagement.productcacheservice.enums.Language;
import com.mta.stockmanagement.productcacheservice.feign.product.ProductServiceFeignClient;
import com.mta.stockmanagement.productcacheservice.repository.ProductRepository;
import com.mta.stockmanagement.productcacheservice.repository.entity.Product;
import com.mta.stockmanagement.productcacheservice.response.ProductResponse;
import com.mta.stockmanagement.productcacheservice.service.ProductRepositoryService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ProductRepositoryServiceImpl implements ProductRepositoryService {

    private final ProductRepository productRepository;
    private final ProductServiceFeignClient productServiceFeignClient;
    @Override
    public Product getbyid(Language language, Long productId) {
        Product product;
        try{
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()){
                product = optionalProduct.get();
            }else {
                log.info("Data yok git evden 3-5 kilo kap gel.");
                product = new Product();
                ProductResponse productResponse = productServiceFeignClient.getbyid(language,productId).getPayload();

                product.setProductId(productResponse.getId());
                product.setName(productResponse.getName());
                product.setPrice(productResponse.getPrice());
                product.setQuantity(productResponse.getQuantity());
                product.setCreatedDate(productResponse.getCreatedDate());
                product.setUpdatedDate(productResponse.getUpdatedDate());
                productRepository.save(product);
            }
        }catch (FeignException.FeignClientException.NotFound exception){
            throw new NotFoundException("Product not found for product id: "+ productId);
        }
        return product;
    }

    @Override
    public void deleteProductsFromCache(Language language) {
        productRepository.deleteAll();
        log.info("Delete All Products");
    }
}
