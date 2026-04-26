package com.test.product_service.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private Double price;
    private String description;
}
