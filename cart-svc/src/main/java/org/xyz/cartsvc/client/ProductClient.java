package org.xyz.cartsvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.xyz.cartsvc.client.dto.ProductBatchReq;
import org.xyz.cartsvc.client.dto.ProductBatchResp;
import org.xyz.cartsvc.client.dto.ProductResponse;
import org.xyz.cartsvc.client.dto.ProductUnitCart;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductClient {

//    @GetMapping("/api/v1/products/{productUnitId}")
//    Optional<ProductResponse> getProductById(@PathVariable("productUnitId") Long productUnitId);

    @GetMapping("/api/v1/products")
    Optional<List<ProductResponse>> getAllProductById(@RequestParam List<Long> ids);

//    @PostMapping("/api/v1/products/batch")
//    Optional<List<ProductBatchResp>> getBatchProductUnitByIds(@RequestBody List<ProductBatchReq> productBatchReqs);

    @PostMapping("/api/v1/products/unit")
    List<ProductUnitCart> getAllProductUnitByIds(@RequestParam List<Long> ids);

    @GetMapping("/api/v1/products/unit/batch/{productId}")
    List<ProductUnitCart> getAllProductUnitByProductId(@PathVariable Long productId);

    @GetMapping("/api/v1/products/unit/{productUnitId}")
    Optional<ProductUnitCart> getProductUnitById(@PathVariable Long productUnitId);

    @GetMapping("/api/v1/products/unit/batch")
    Optional<List<ProductBatchResp>> getBatchProductUnitByIds(@RequestParam List<Long> productUnitIds);

}
