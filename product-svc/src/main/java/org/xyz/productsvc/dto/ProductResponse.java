package org.xyz.productsvc.dto;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        boolean isSeries,
        List<String> images,
        List<String> imageContents,
        Long categoryId,
        List<ProductUnitResponse> productUnits
) {
}
