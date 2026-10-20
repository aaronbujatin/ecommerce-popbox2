package org.xyz.cartsvc.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductBatchResp(
        Long productId,
        Long id,
        String name,
        BigDecimal price,
        String type,
        int stock,
        String description,
        List<String> images
) {
}
