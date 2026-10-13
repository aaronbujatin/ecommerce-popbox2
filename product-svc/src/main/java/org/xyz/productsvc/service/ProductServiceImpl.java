package org.xyz.productsvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xyz.productsvc.dto.*;
import org.xyz.productsvc.entity.Product;
import org.xyz.productsvc.enums.ProductErrorInfo;
import org.xyz.productsvc.exception.ResourceNotFoundException;
import org.xyz.productsvc.mapper.ProductMapper;
import org.xyz.productsvc.repository.dto.ProductCartRespRecord;
import org.xyz.productsvc.repository.ProductRepository;
import org.xyz.productsvc.repository.projections.ProductCartRespProjection;
import org.xyz.productsvc.repository.specs.ProductSpecs;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

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
        return null;
    }

    @Override
    public List<ProductResponse> getAllProductsByFilter(List<ProductBatchReq> productBatchReqs) {
        return List.of();
    }

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

    @Override
    public List<ProductResponse> getAllProductByFilter(ProductFilterParam filterParam) {
        var products = productRepository.findProductByFilter(
                filterParam.categoryId(),
                filterParam.productId()
        );

        return products.stream()
                .map(p -> {
                            try {
                                return new ProductResponse(
                                        p.getId(),
                                        p.getName(),
                                        p.getDescription(),
                                        p.getIsSeries(),
                                        p.getImages(),
                                        p.getImageContents(),
                                        p.getCategoryId(),
                                        objectMapper.readValue(
                                                p.getProductUnits(),
                                                new TypeReference<List<ProductUnitResponse>>() {
                                            })
                                            .stream()
                                            .map(pu -> new ProductUnitResponse(
                                                    pu.id(),
                                                    pu.type(),
                                                    pu.price(),
                                                    pu.stockQty()
                                                )
                                            )
                                            .toList()
                                    );
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .toList();
    }


    private ProductResponse mapToProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.isSeries(),
                product.getImages(),
                product.getImageContents(),
                product.getCategory().getId(),
                product.getProductUnits()
                        .stream()
                        .map(unit ->
                            new ProductUnitResponse(
                                unit.getId(),
                                unit.getType(),
                                unit.getPrice(),
                                unit.getStockQty()
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

        return Arrays.asList(images.replaceAll("[{}\"']", "").split(","));
    }


}
