package org.xyz.productsvc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.xyz.productsvc.entity.Product;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByProductId_returnProduct_whenExists() {
        Product product = Product.builder()
                .id(1L)
                .build();
        productRepository.save(product);

        Optional<Product> savedProduct = productRepository.findById(1L);

        assertThat(savedProduct).isPresent();
        assertThat(savedProduct.get().getId()).isEqualTo(1L);

    }
}