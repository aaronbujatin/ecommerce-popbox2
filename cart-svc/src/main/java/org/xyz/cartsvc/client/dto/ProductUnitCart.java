package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductUnitCart(
        Long productId,
        Long productUnitId,
        String name,
        String unitType,
        int stock,
        BigDecimal price,
        List<String> images
) {
}
