package org.xyz.productsvc.dto;

public record ProductFilterParam(
        Long productId,
        Long categoryId
) {
}
