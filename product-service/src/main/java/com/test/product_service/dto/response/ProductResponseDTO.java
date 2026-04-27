package com.test.product_service.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
}
