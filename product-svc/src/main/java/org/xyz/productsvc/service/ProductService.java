package org.xyz.productsvc.service;


import org.xyz.productsvc.dto.*;
import org.xyz.productsvc.repository.dto.ProductUnitDto;

import java.util.List;

public interface ProductService {

    String createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllProductsByFilter(List<ProductBatchReq> productBatchReqs);
    List<ProductUnitDto> getAllProductUnitById(List<Long> productUnitIds);
    ProductCartResp getProductUnitCartById(Long productId);
    List<ProductResponse> getAllProductByFilter(ProductFilterParam filterParam);
    ProductUnitDto getProductUnitById(Long productUnitId);
}
