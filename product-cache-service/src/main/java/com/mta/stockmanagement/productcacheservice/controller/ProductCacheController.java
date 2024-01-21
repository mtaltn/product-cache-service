package com.mta.stockmanagement.productcacheservice.controller;

import com.mta.stockmanagement.productcacheservice.enums.Language;
import com.mta.stockmanagement.productcacheservice.repository.entity.Product;
import com.mta.stockmanagement.productcacheservice.response.InternalApiResponse;
import com.mta.stockmanagement.productcacheservice.response.ProductResponse;
import com.mta.stockmanagement.productcacheservice.service.ProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1.0/product-cache")
@Slf4j
@RequiredArgsConstructor
public class ProductCacheController {

    private final ProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{language}/get/{id}")
    public InternalApiResponse<ProductResponse> getbyid(@PathVariable("language")Language language,
                                                        @PathVariable("Id") Long id){
        log.debug("[{}][getbyid] -> request id : {}",this.getClass().getSimpleName(),id);
        Product product = productRepositoryService.getbyid(language,id);
        ProductResponse productResponse = productRepositoryService.convertProductResponse(product);
        return  InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/removeCache")
    public void removeProductsFromCache(@PathVariable("language") Language language){
        productRepositoryService.deleteProductsFromCache(language);
    }


}
