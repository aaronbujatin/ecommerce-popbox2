package org.xyz.productsvc.dto;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        List<String> images,
        String categoryName,
        List<ProductUnitResponse> productUnits
) {
}
