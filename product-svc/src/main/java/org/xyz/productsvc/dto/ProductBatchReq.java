package org.xyz.productsvc.dto;

import java.util.List;

public record ProductBatchReq(
        Long productId,
        List<Long> productUnitIds
) {
}
