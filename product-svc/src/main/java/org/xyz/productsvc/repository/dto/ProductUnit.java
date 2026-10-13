package org.xyz.productsvc.repository.dto;

import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;

public record ProductUnit(
        Long id,
        BigDecimal price,
        Integer stockQty,
        ProductUnitType type
) {
}
