package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductBatchResp(
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
