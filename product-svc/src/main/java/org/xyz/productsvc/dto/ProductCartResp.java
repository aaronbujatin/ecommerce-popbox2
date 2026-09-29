package org.xyz.productsvc.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCartResp(
        Long productId,
        Long productUnitId,
        String name,
        BigDecimal price,
        String unitType,
        int stock,
        String description,
        List<String> images
) {
}
