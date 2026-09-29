package org.xyz.productsvc.dto;

import lombok.Builder;
import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;

@Builder
public record ProductUnitRequest(
        ProductUnitType productUnitType,
        BigDecimal price,
        String imageUrl,
        int stock
) {
}
