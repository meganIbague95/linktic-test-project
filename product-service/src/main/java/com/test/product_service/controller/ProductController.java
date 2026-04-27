package com.test.product_service.controller;

import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.service.ProductService;
import com.test.product_service.util.JsonApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations over products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create a product")
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

    @Operation(summary = "Get a product detail")
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

    @Operation(summary = "Get all products")
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
