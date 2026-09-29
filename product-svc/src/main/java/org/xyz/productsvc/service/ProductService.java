package org.xyz.productsvc.service;


import org.xyz.productsvc.dto.*;

import java.util.List;

public interface ProductService {

    String createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllProductsByFilter(List<ProductBatchReq> productBatchReqs);
//    List<ProductCartResp> getAllProductUnitById(List<Long> ids);
    List<ProductCartResp> getAllProductUnitById(List<Long> productUnitIds);
    ProductCartResp getProductUnitById(Long productId);
}
