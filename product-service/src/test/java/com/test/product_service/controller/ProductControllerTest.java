package com.test.product_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Laptop");
        dto.setPrice(1000.0);

        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1L)
                .name("Laptop")
                .price(1000.0)
                .build();

        Mockito.when(service.create(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.attributes.name").value("Laptop"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1L)
                .name("Laptop")
                .price(1000.0)
                .build();

        Mockito.when(service.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        List<ProductResponseDTO> list = List.of(
                ProductResponseDTO.builder().id(1L).name("Laptop").price(1000.0).build()
        );

        Mockito.when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}
