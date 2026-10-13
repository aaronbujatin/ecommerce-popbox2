package org.xyz.cartsvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xyz.cartsvc.dto.CartConvertRequest;
import org.xyz.cartsvc.dto.CartItemRequest;
import org.xyz.cartsvc.dto.CartItemResponse;
import org.xyz.cartsvc.dto.CartResponse;
import org.xyz.cartsvc.service.CartService;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items/increment")
    public ResponseEntity<String> addCartItem(@RequestBody CartItemRequest cartItemRequest) {
        log.info("Receiving request {}", cartItemRequest);
        cartService.addCartItem(cartItemRequest);
        return ResponseEntity.ok("Successfully added to the cart");
    }

    @PostMapping("/items/decrement")
    public ResponseEntity<String> removeCartItem(@RequestBody CartItemRequest cartItemRequest) {
        log.info("Receiving request {}", cartItemRequest);
        cartService.removeCartItem(cartItemRequest);
        return ResponseEntity.ok("Successfully removed from cart");
    }

    @GetMapping("/items/user/{userId}")
    public ResponseEntity<CartResponse> getCartByUserId(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(cartService.getCartByUserId(id));
    }

    @PostMapping("/items/user/convert")
    public ResponseEntity<CartResponse> convertCart(@RequestBody CartConvertRequest cartConvertRequest) {
        return ResponseEntity.ok(cartService.convertCart(cartConvertRequest));
    }


}
