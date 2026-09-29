package org.xyz.productsvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.xyz.productsvc.dto.ProductRequest;
import org.xyz.productsvc.dto.ProductResponse;
import org.xyz.productsvc.dto.ProductUnitRequest;
import org.xyz.productsvc.dto.ProductUnitResponse;
import org.xyz.productsvc.enums.ProductUnitType;
import org.xyz.productsvc.mapper.ProductMapper;
import org.xyz.productsvc.service.ProductService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private final String PRODUCT_API_ENDPOINT = "/api/v1/products";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductMapper productMapper;


    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = ProductRequest
                .builder()
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


        when(productService.createProduct(productRequest)).thenReturn("product successfully saved");

        mockMvc.perform(post(PRODUCT_API_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("product successfully saved"));


    }

    @Test
    void testGetProductById() throws Exception {
        ProductResponse productResponse = new ProductResponse(
                1L,
                "SKULLPANDA × Wednesday Plush（Nevermore Academy Uniform Version",
                "...",
                List.of("..."),
                "category1",
                List.of(ProductUnitResponse.builder().build())
        );


        when(productService.getProductById(1L)).thenReturn(productResponse);

        mockMvc.perform(get(PRODUCT_API_ENDPOINT + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("SKULLPANDA × Wednesday Plush（Nevermore Academy Uniform Version")
        );


    }
}