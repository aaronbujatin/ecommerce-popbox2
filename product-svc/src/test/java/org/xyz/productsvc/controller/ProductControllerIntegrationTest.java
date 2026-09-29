package org.xyz.productsvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.xyz.productsvc.dto.ProductRequest;
import org.xyz.productsvc.dto.ProductUnitRequest;
import org.xyz.productsvc.enums.ProductUnitType;
import org.xyz.productsvc.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    private final String PRODUCT_API_ENDPOINT = "/api/v1/products";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct_andPersistToDatabase() throws Exception {

        var productRequest = ProductRequest.builder()
                .name("test name")
                .description("test desc")
                .price(BigDecimal.valueOf(1000))
                .images(List.of("image_test"))
                .categoryId(1L)
                .productUnitRequests(List.of(
                                new ProductUnitRequest(
                                        ProductUnitType.SINGLE_BOX,
                                        BigDecimal.valueOf(1000),
                                        "image_url",
                                        10
                                )
                        )
                )
                .build();

        var requestBody = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post(PRODUCT_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        assertEquals(14, productRepository.count());


    }
}