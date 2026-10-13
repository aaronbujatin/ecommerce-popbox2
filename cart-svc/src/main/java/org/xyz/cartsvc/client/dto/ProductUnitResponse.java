package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;

public record ProductUnitResponse(
        Long id,
        String productUnitType,
        BigDecimal price,
        int stock
) {
}
