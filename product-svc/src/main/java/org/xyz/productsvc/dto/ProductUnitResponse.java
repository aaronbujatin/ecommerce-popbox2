package org.xyz.productsvc.dto;

import lombok.Builder;
import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;

@Builder
public record ProductUnitResponse(
        Long id,
        ProductUnitType type,
        BigDecimal price,
        int stockQty
) {
}
