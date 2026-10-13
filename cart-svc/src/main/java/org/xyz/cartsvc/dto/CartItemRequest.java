package org.xyz.cartsvc.dto;

public record CartItemRequest(
        Long cartId,
        Long userId,
        Long productId,
        Long productUnitId,
        int quantity
) {
}