package org.xyz.cartsvc.dto;

import java.util.List;

public record ConvertProductReq(
        Long productId,
        List<Long> productUnitIds
) {
}
