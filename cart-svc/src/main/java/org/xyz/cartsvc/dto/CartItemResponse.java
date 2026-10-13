package org.xyz.cartsvc.dto;

import org.xyz.cartsvc.enums.CartItemStatus;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long productId,
        Long productUnitId,
        String name,
        BigDecimal price,
        String image,
        String unitType,
        int quantity,
        BigDecimal subTotal,
        CartItemStatus status
) {
}