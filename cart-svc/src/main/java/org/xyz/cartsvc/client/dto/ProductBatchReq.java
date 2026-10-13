package org.xyz.cartsvc.client.dto;

import java.util.List;

public record ProductBatchReq(
        Long productId,
        List<Long> productUnitIds
) {
}
