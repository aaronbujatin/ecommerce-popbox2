package org.xyz.productsvc.dto;

public record UserResponse(
        Long id,
        String username,
        String name
) {
}
