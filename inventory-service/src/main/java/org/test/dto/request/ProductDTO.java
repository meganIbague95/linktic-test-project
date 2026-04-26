package org.test.dto.request;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
}
