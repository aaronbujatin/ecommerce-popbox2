package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductUnitCart(
        Long productId,
        Long id,
        String name,
        String unitType,
        int stockQty,
        BigDecimal price,
        List<String> images
) {
}
