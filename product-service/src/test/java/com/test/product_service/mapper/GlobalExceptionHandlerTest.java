package com.test.product_service.mapper;

import com.test.product_service.controller.ProductController;
import com.test.product_service.exception.GlobalExceptionHandler;
import com.test.product_service.exception.ProductNotFoundException;
import com.test.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        Mockito.when(service.getById(99L))
                .thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].status").value("404"))
                .andExpect(jsonPath("$.errors[0].title").value("Product Not Found"));
    }
}
