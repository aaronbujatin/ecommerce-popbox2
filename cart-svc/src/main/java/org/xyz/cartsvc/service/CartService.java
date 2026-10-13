package org.xyz.cartsvc.service;

import org.xyz.cartsvc.dto.CartConvertRequest;
import org.xyz.cartsvc.dto.CartItemRequest;
import org.xyz.cartsvc.dto.CartResponse;

public interface CartService {

    CartResponse addCartItem(CartItemRequest cartItemRequest);
    CartResponse removeCartItem(CartItemRequest cartItemRequest);
    CartResponse getCartByUserId(Long id);
    CartResponse convertCart(CartConvertRequest cartConvertRequest);




}
