package org.xyz.cartsvc.util;

import org.xyz.cartsvc.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartUtil {

    public static BigDecimal calculateTotalPrice(int quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static BigDecimal calculateCartItemTotalPrice(List<CartItem> cartItems) {
        return cartItems
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


}
