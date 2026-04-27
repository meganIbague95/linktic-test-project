package com.test.product_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@TestPropertySource(properties = {"security.api-key=secret123"})
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_KEY = "secret123";

    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("phone");
        request.setPrice(100.0);
        request.setDescription("desc");

        ProductResponseDTO response = ProductResponseDTO.builder().id(1L).name("phone").build();

        when(service.create(request)).thenReturn(response);

        mockMvc.perform(post("/products")
                        .header("X-API-KEY", API_KEY)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.attributes.name").value("phone"));
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {

        when(service.getById(1L))
                .thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/products/1")
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isInternalServerError());
    }
}
