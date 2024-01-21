package com.mta.stockmanagement.productcacheservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Product")
public class Product implements Serializable {

    @Id
    private Long Id;
    private String name;
    private Integer quantity;
    private Double price;
    private Long createdDate;
    private Long updatedDate;
    private boolean deleted;
}
