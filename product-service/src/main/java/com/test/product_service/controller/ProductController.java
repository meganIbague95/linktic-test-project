package com.test.product_service.controller;

import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.service.ProductService;
import com.test.product_service.util.JsonApiConstants;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = service.create(dto);

        return Map.of(
                JsonApiConstants.DATA, Map.of(
                        JsonApiConstants.ID, product.getId(),
                        JsonApiConstants.TYPE, JsonApiConstants.PRODUCT,
                        JsonApiConstants.ATTRIBUTES, product
                )
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        ProductResponseDTO product = service.getById(id);

        return Map.of(
                JsonApiConstants.DATA, Map.of(
                        JsonApiConstants.ID, product.getId(),
                        JsonApiConstants.TYPE, JsonApiConstants.PRODUCT,
                        JsonApiConstants.ATTRIBUTES, product
                )
        );
    }

    @GetMapping
    public Map<String, Object> getAll() {
        return Map.of(
                JsonApiConstants.DATA, service.getAll().stream().map(p -> Map.of(
                        JsonApiConstants.ID, p.getId(),
                        JsonApiConstants.TYPE, JsonApiConstants.PRODUCT,
                        JsonApiConstants.ATTRIBUTES, p
                )).toList()
        );
    }
}
