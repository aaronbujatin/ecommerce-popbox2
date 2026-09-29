package org.xyz.productsvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xyz.productsvc.dto.*;
import org.xyz.productsvc.entity.Product;
import org.xyz.productsvc.enums.ProductErrorInfo;
import org.xyz.productsvc.exception.ResourceNotFoundException;
import org.xyz.productsvc.mapper.ProductMapper;
import org.xyz.productsvc.repository.dto.ProductCartRespRecord;
import org.xyz.productsvc.repository.ProductRepository;
import org.xyz.productsvc.repository.projections.ProductCartRespProjection;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public String createProduct(ProductRequest productRequest) {
        log.info("Creating product with name: {}", productRequest.name());
        productRepository.save(productMapper.mapToProduct(productRequest));

        return "product successfully saved";

    }

    @Override
    public ProductResponse getProductById(Long id) {

        log.info("Getting the product with id of {}", id);
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProductErrorInfo.PRODUCT_NOT_FOUND));

        ProductResponse productResponse = mapToProductResponse(product);

        log.info("Returning the product with id of {} {}", id, productResponse);
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
//        log.info("Getting the list of product");
//
//        List<Product> products = productRepository.findAll();
//
//        List<ProductResponse> productResponses = products
//                .stream()
//                .map(productMapper::mapToProductResponse)
//                .toList();
//
//        log.info("Returning list of products {}", productResponses);
//        return productResponses;

        return null;
    }

    @Override
    public List<ProductResponse> getAllProductsByFilter(List<ProductBatchReq> productBatchReqs) {

//        var productIds = productBatchReqs.stream().map(ProductBatchReq::id).toList();

//        var product = productRepository.findAllById(productIds)
//                .stream()
//                .map(p -> new ProductResponse(
//                            p.getId(),
//                            p.getName(),
//                            p.getDescription(),
//                            p.getImages(),
//                            p.getCategory(),
//                            p.getProductUnits()
//                                    .stream()
//                                    .filter()
//                        )
//                );


        return List.of();
    }

//    @Override
//    public List<ProductCartResp> getAllProductUnitById(List<Long> ids) {
//        return productRepository.findAllProductUnitById(ids)
//                .stream()
//                .map(product -> new ProductCartResp(
//                        product.productId(),
//                        product.productUnitId(),
//                        product.name(),
//                        product.price(),
//                        product.unitType(),
//                        product.stock(),
//                        product.description(),
//                        convertImages(product.images()).toString()
//                ))
//                .toList();
//    }

    @Override
    public List<ProductCartResp> getAllProductUnitById(List<Long> productUnitIds) {
        return productRepository.findAllProductUnitById(productUnitIds)
                .stream()
                .map(product -> new ProductCartResp(
                        product.getProductId(),
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getProductUnitType(),
                        product.getStock(),
                        product.getDescription(),
                        product.getImages()
                ))
                .toList();
    }

    @Override
    public ProductCartResp getProductUnitById(Long productUnitId) {
        var productCartRespProjection = productRepository.findProductUnitById(productUnitId)
                .orElseThrow(() -> new ResourceNotFoundException(ProductErrorInfo.PRODUCT_UNIT_NOT_FOUND));
        return mapToProductCartResp(productCartRespProjection);
    }

    private ProductResponse mapToProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImages(),
                product.getCategory().getName(),
                product.getProductUnits()
                        .stream()
                        .map(unit -> new ProductUnitResponse(
                                        unit.getId(),
                                        unit.getProductUnitType(),
                                        unit.getPrice(),
                                        unit.getStock(),
                                        unit.getImageUrl()
                                )
                        )
                        .toList()
        );
    }

    private ProductCartResp mapToProductCartResp(ProductCartRespProjection productCartRespProjection) {
        return new ProductCartResp(
                productCartRespProjection.getProductId(),
                productCartRespProjection.getId(),
                productCartRespProjection.getName(),
                productCartRespProjection.getPrice(),
                productCartRespProjection.getProductUnitType(),
                productCartRespProjection.getStock(),
                productCartRespProjection.getDescription(),
                productCartRespProjection.getImages()
        );
    }

    private ProductCartResp mapToProductCartResp(ProductCartRespRecord productCartRespRecord) {
        return new ProductCartResp(
                productCartRespRecord.id(),
                productCartRespRecord.productId(),
                productCartRespRecord.name(),
                productCartRespRecord.price(),
                productCartRespRecord.productUnitType(),
                productCartRespRecord.stock(),
                productCartRespRecord.description(),
                productCartRespRecord.images()
        );
    }

    private List<String> convertImages(String dbValue) {
        if (dbValue == null) return List.of();

        // If stored like: ["img1.jpg","img2.jpg"]
        return Arrays.stream(
                        dbValue.replace("[", "")
                                .replace("]", "")
                                .replace("\"", "")
                                .split(",")
                ).map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private List<String> parseImages(String images) {
        if (images == null) return List.of();
        // if stored as Postgres array e.g. {"img1.png","img2.png"}
        return Arrays.asList(images.replaceAll("[{}\"']", "").split(","));
        // if stored as JSON array e.g. ["img1.png","img2.png"], use ObjectMapper instead
    }


}
