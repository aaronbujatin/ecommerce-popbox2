package org.xyz.productsvc.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductRequest(
        @NotEmpty(message = "name is required")
        String name,
        @NotEmpty(message = "description cannot be empty")
        String description,
        @Min(message = "invalid price", value = 1)
        BigDecimal price,
        List<@NotEmpty(message = "image address is required") String> images,
        @NotNull(message = "category id is required")
        Long categoryId,
        List<ProductUnitRequest> productUnitRequests
) {

}
