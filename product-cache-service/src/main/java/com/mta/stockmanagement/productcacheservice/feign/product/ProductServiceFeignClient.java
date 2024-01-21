package com.mta.stockmanagement.productcacheservice.feign.product;

import com.mta.stockmanagement.productcacheservice.enums.Language;
import com.mta.stockmanagement.productcacheservice.response.InternalApiResponse;
import com.mta.stockmanagement.productcacheservice.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${feign.product-service.name}")
public interface ProductServiceFeignClient {

    @GetMapping(value = "/api/v1.0/product/{language}/products/{id}")
    InternalApiResponse<ProductResponse> getbyid(@PathVariable("language") Language language,
                                                 @PathVariable("id") Long id);
}
