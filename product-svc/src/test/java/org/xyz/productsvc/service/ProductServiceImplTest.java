package org.xyz.productsvc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xyz.productsvc.dto.ProductRequest;
import org.xyz.productsvc.dto.ProductUnitRequest;
import org.xyz.productsvc.entity.Product;
import org.xyz.productsvc.entity.ProductUnit;
import org.xyz.productsvc.enums.ProductUnitType;
import org.xyz.productsvc.exception.ResourceNotFoundException;
import org.xyz.productsvc.mapper.ProductMapper;
import org.xyz.productsvc.repository.dto.ProductCartRespRecord;
import org.xyz.productsvc.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProduct_whenValidRequest() {

        //Arrange
         var productReq = new ProductRequest(
                 "test name",
                 "test desc",
                 BigDecimal.valueOf(10L),
                 List.of("img1", "img2"),
                 1L,
                 List.of(
                         new ProductUnitRequest(ProductUnitType.SINGLE_BOX, BigDecimal.valueOf(150), "imageUrl", 100)
                 )
         );

        var mappedProduct = Product.builder()
                .name(productReq.name())
                .description(productReq.description())
                .build();

         var product = Product.builder()
                 .id(1L)
                 .name("test")
                 .description("desc")
                 .images(List.of("img1", "img2"))
                 .productUnits(
                         List.of(
                                 ProductUnit.builder().stock(100).imageUrl("imgurl").productUnitType(ProductUnitType.SINGLE_BOX).price(BigDecimal.valueOf(1200)).build(),
                                 ProductUnit.builder().stock(100).imageUrl("imgurl").productUnitType(ProductUnitType.WHOLE_SET).price(BigDecimal.valueOf(11000)).build()
                         )
                 )
                 .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when(productMapper.mapToProduct(productReq)).thenReturn(mappedProduct);
//        productService.createProduct(productReq);

        //Act
        var result = productRepository.save(product);

        //Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProductUnits().get(0).getProductUnitType()).isEqualTo(ProductUnitType.SINGLE_BOX);
    }


    @Test
    void should_ReturnProductUnit_when_ProductUnitIdExists() {
        //Arrange
        var productUnitId = 1L;
        var productUnitRecord = new ProductCartRespRecord(
                2L,
                productUnitId,
                "name",
                BigDecimal.valueOf(100.00),
                ProductUnitType.SINGLE_BOX.toString(),
                100,
                "desc",
                List.of("img1", "img2")
        );
        when(productRepository.findProductUnitByIdRecord(any(Long.class))).thenReturn(Optional.of(productUnitRecord));
        productService.getProductUnitById(productUnitId);
        //Act
        var result = productRepository.findProductUnitByIdRecord(productUnitId);

        //Assert
        assertThat(result.get().id()).isEqualTo(2L);

    }

    @Test
    void should_ThrowResourceNotFoundException_when_ProductIdDoesNotExist() {
        //Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        //Act and Assert
        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found");
    }
}