package org.xyz.productsvc.repository.dto;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;
import java.util.List;

public record ProductUnitDto(
        Long id,
        Long productId,
        String name,
        BigDecimal price,
        Integer stockQty,
        ProductUnitType type,
        @JdbcTypeCode(SqlTypes.ARRAY)
        List<String> images
) {
}
