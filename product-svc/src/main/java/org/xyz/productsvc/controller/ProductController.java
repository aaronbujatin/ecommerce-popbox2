package org.xyz.productsvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.xyz.productsvc.dto.*;
import org.xyz.productsvc.repository.ProductRepository;
import org.xyz.productsvc.repository.dto.ProductUnitDto;
import org.xyz.productsvc.service.ProductService;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        var response = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> getAllProductsByFilter(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long productId
    ) {
        var params = new ProductFilterParam(categoryId, productId);
        var body = productService.getAllProductByFilter(params);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/unit/{productUnitId}")
    public ResponseEntity<ProductUnitDto> getProductUnitById(
            @PathVariable("productUnitId") Long productUnitId
    ) {
        ProductUnitDto productUnitDto = productService.getProductUnitById(productUnitId);
        return ResponseEntity.ok(productUnitDto);
    }

    @GetMapping("/unit/batch")
    public ResponseEntity<List<ProductUnitDto>> getProductUnitBatchById(
            @RequestParam("productUnitIds") List<Long> productUnitIds
    ) {
        var productUnitDtos = productService.getAllProductUnitById(productUnitIds);
        return ResponseEntity.ok(productUnitDtos);
    }


}
