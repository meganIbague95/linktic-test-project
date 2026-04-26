package com.test.product_service.service;

import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.exception.ProductNotFoundException;
import com.test.product_service.model.Product;
import com.test.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void shouldCreateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Laptop");
        dto.setPrice(1000.0);
        dto.setDescription("Gaming");

        Product saved = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(1000.0)
                .description("Gaming")
                .build();

        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(saved);

        ProductResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1000.0, result.getPrice());
    }

    @Test
    void shouldGetProductById() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(1000.0)
                .description("Gaming")
                .build();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = List.of(
                Product.builder().id(1L).name("Laptop").price(1000.0).build(),
                Product.builder().id(2L).name("Mouse").price(50.0).build()
        );

        Mockito.when(repository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }
}
