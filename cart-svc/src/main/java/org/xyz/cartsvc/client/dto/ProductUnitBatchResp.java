package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;

public record ProductUnitBatchResp(
        Long id,
        String unitType,
        BigDecimal price,
        int stock,
        String  imageUrl
) {
}
