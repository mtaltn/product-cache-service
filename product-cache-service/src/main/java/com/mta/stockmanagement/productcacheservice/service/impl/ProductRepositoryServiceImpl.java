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


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductRepositoryServiceImpl implements ProductRepositoryService {

    private final ProductRepository productRepository;
    private final ProductServiceFeignClient productServiceFeignClient;
    @Override
    public Product getbyid(Language language, Long Id) {
        Product product;
        try{
            Optional<Product> optionalProduct = productRepository.findById(Id);
            if (optionalProduct.isPresent()){
                product = optionalProduct.get();
            }else {
                log.info("Data yok git evden 3-5 kilo kap gel.");
                product = new Product();
                ProductResponse productResponse = productServiceFeignClient.getbyid(language,Id).getPayload();

                product.setId(productResponse.getId());
                product.setName(productResponse.getName());
                product.setPrice(productResponse.getPrice());
                product.setQuantity(productResponse.getQuantity());
                product.setCreatedDate(productResponse.getCreatedDate());
                product.setUpdatedDate(productResponse.getUpdatedDate());
                productRepository.save(product);
            }
        }catch (FeignException.FeignClientException.NotFound exception){
            throw new NotFoundException("Product not found for product id: "+ Id);
        }
        return product;
    }

    @Override
    public void deleteProductsFromCache(Language language) {
        productRepository.deleteAll();
        log.info("Delete All Products");
    }

    @Override
    public ProductResponse convertProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .createdDate(product.getCreatedDate())
                .updatedDate(product.getUpdatedDate())
                .build();
    }

    @Override
    public List<ProductResponse> convertProductResponseList(List<Product> productList){
        return productList.stream()
                .map(arg -> ProductResponse.builder()
                        .id(arg.getId())
                        .name(arg.getName())
                        .quantity(arg.getQuantity())
                        .price(arg.getPrice())
                        .createdDate(arg.getCreatedDate())
                        .updatedDate(arg.getUpdatedDate())
                        .build())
                .collect(Collectors.toList());
    }


}
