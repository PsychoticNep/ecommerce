package com.ecommerce.productapi.controller;

import com.ecommerce.productapi.dto.ProductRequest;
import com.ecommerce.productapi.dto.ProductResponse;
import com.ecommerce.productapi.model.ProductStatus;
import com.ecommerce.productapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_ShouldReturn201() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Test Product")
                .price(new BigDecimal("29.99"))
                .category("Test")
                .stockQuantity(10)
                .build();

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .name("Test Product")
                .price(new BigDecimal("29.99"))
                .category("Test")
                .stockQuantity(10)
                .status(ProductStatus.ACTIVE)
                .build();

        when(productService.createProduct(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Test Product"));
    }

    @Test
    void getAllProducts_ShouldReturn200() throws Exception {
        Page<ProductResponse> page = new PageImpl<>(List.of());
        when(productService.getAllProducts(anyInt(), anyInt(), anyString(), anyString())).thenReturn(page);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
