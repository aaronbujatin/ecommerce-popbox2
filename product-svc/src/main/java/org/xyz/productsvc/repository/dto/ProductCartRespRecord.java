package org.xyz.productsvc.repository.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCartRespRecord(
        Long id,
        Long productId,
        String name,
        BigDecimal price,
        String productUnitType,
        Integer stock,
        String description,
        List<String> images
) {

}
