package org.xyz.cartsvc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CartItemStatus {
    ACTIVE("Cart is active and being used by shopper"),
    BEGIN_CHECKOUT("User initiated the checkout process"),
    ORDERED("Transaction for the cart is successfully convert and ordered");

    private final String description;
}
