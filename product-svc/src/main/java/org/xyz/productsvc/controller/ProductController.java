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
import org.xyz.productsvc.service.ProductService;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        var response = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @PostMapping("/unit")
//    public ResponseEntity<List<ProductCartResp>> getAllProductUnitById(@RequestParam List<Long> ids) {
//        var respBody = productService.getAllProductUnitByProductId(ids);
//        return ResponseEntity.ok(respBody);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

//    @GetMapping()
//    public ResponseEntity<List<ProductResponse>> getAllProducts() {
//        List<ProductResponse> productResponses = productService.getAllProducts();
//        return ResponseEntity.ok(productRepository.findAll()
//                .stream()
//                .map(product -> new ProductResponse(
//                        product.getId(),
//                        product.getName(),
//                        product.getDescription(),
//                        product.getImages(),
//                        product.getCategory().getName(),
//                        product.getProductUnits()
//                                .stream()
//                                .map(productUnit -> new ProductUnitResponse(
//                                        productUnit.getId(),
//                                        productUnit.getProductUnitType(),
//                                        productUnit.getPrice(),
//                                        productUnit.getStock(),
//                                        productUnit.getImageUrl()
//                                ))
//                                .toList()
//                )).toList());
//    }


    @GetMapping("/unit/batch")
    public ResponseEntity<List<ProductCartResp>> getAllProductUnitByProductId(@RequestParam List<Long> productUnitIds) {
        var body = productService.getAllProductUnitById(productUnitIds);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/unit/{productUnitId}")
    public ResponseEntity<ProductCartResp> getProductUnitByProductId(@PathVariable Long productUnitId) {
        var body = productService.getProductUnitById(productUnitId);
        return ResponseEntity.ok(body);
    }

}
