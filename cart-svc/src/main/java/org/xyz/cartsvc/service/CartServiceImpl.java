package org.xyz.cartsvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.xyz.cartsvc.client.ProductClient;
import org.xyz.cartsvc.client.UserClient;
import org.xyz.cartsvc.client.dto.*;
import org.xyz.cartsvc.dto.*;
import org.xyz.cartsvc.entity.Cart;
import org.xyz.cartsvc.entity.CartItem;
import org.xyz.cartsvc.enums.CartErrorInfo;
import org.xyz.cartsvc.enums.CartItemStatus;
import org.xyz.cartsvc.enums.CartReqStatus;
import org.xyz.cartsvc.exception.InvalidCartRequestException;
import org.xyz.cartsvc.exception.ResourceNotFoundException;
import org.xyz.cartsvc.repository.CartItemRepository;
import org.xyz.cartsvc.repository.CartRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.xyz.cartsvc.util.CartUtil.calculateCartItemTotalPrice;
import static org.xyz.cartsvc.util.CartUtil.calculateTotalPrice;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final CartItemRepository cartItemRepository;

    public CartResponse addCartItem(CartItemRequest cartItemRequest) {
        var userResp = this.userClient.getUserById(cartItemRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.USER_NOT_FOUND));

        var productUnitResp = productClient.getProductUnitById(cartItemRequest.productUnitId())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.PRODUCT_UNIT_NOT_FOUND));

        if (cartItemRequest.quantity() >= productUnitResp.stock()) {
            throw new InvalidCartRequestException(CartErrorInfo.PRODUCT_OUT_OF_STOCK);
        }

        Cart cart = cartRepository.findByUserId(userResp.id())
                .orElseGet(() -> {
                    log.info("🛒No cart for user productUnitId [{}]. ✅Creating new cart", userResp.id());
                    var newCart = Cart
                            .builder()
                            .userId(userResp.id())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())

                            .build();
                    return cartRepository.save(newCart);
                });


        var cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProductUnitId().equals(productUnitResp.productUnitId())
                        && item.getStatus().equals(CartItemStatus.ACTIVE)
                )
                .findFirst()
                .map(item -> {
                    log.info("🔁Updating item with product productUnitId [{}].", productUnitResp.productUnitId());
                    var quantity = item.getQuantity() + cartItemRequest.quantity();
                    var cartItemTotalPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(quantity));

                    if (quantity >= productUnitResp.stock()) {
                        throw new InvalidCartRequestException(CartErrorInfo.PRODUCT_OUT_OF_STOCK);
                    }

                    item.setQuantity(quantity);
                    item.setTotalPrice(cartItemTotalPrice);
                    item.setUpdatedAt(LocalDateTime.now());
                    return item;
                })
                .orElseGet(() -> {
                    log.info("🙅No existing cart item for product productUnitId {}. ✅Creating new cart item", productUnitResp.productId());
                    return CartItem
                            .builder()
                            .cart(cart)
                            .status(CartItemStatus.ACTIVE)
                            .productId(productUnitResp.productId())
                            .unitPrice(productUnitResp.price())
                            .productUnitId(productUnitResp.productUnitId())
                            .quantity(cartItemRequest.quantity())
                            .totalPrice(calculateTotalPrice(cartItemRequest.quantity(), productUnitResp.price()))
                            .build();
                    }
                );

        cart.addToCart(cartItem);
        cartRepository.save(cart);

        return new CartResponse(
                cart.getId(),
                userResp.id(),
                cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add),
                cart.getCartItems()
                        .stream()
                        .map(item -> new CartItemResponse(
                                    item.getId(),
                                    item.getProductId(),
                                    item.getProductUnitId(),
                                    productUnitResp.name(),
                                    item.getUnitPrice(),
                                    productUnitResp.images().get(0),
                                    productUnitResp.unitType(),
                                    item.getQuantity(),
                                    item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())),
                                    item.getStatus()
                                )
                        )
                        .toList(),
                null
        );
    }

    @Override
    public CartResponse removeCartItem(CartItemRequest cartItemRequest) {
        var userResp = this.userClient.getUserById(cartItemRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.USER_NOT_FOUND));

        var extProductUnitResp = productClient.getProductUnitById(cartItemRequest.productUnitId())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.PRODUCT_UNIT_NOT_FOUND));

        if (cartItemRequest.quantity() >= extProductUnitResp.stock()) {
            throw new InvalidCartRequestException(CartErrorInfo.PRODUCT_UNIT_OUT_OF_STOCK);
        }

        Cart cart = cartRepository.findByUserId(userResp.id())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.CART_NOT_FOUND));

        cart.getCartItems()
                .stream()
                .filter(item -> item.getProductUnitId().equals(extProductUnitResp.productUnitId())
                        && item.getStatus().equals(CartItemStatus.ACTIVE)
                )
                .findFirst()
                .map(item -> {
                    if (item.getQuantity() != 1) {
                        log.info("🔁Updating cart item quantity with product productUnitId [{}].", extProductUnitResp.productUnitId());
                        var quantity = item.getQuantity() - cartItemRequest.quantity();
                        var cartItemTotalPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
                        item.setQuantity(quantity);
                        item.setTotalPrice(cartItemTotalPrice);
                        item.setUpdatedAt(LocalDateTime.now());
                    } else {
                        log.info("✖️Removing to cart item with product productUnitId [{}].", extProductUnitResp.productUnitId());
                        cart.removeToCart(item);
                    }

                    return cartRepository.save(cart);
                })
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.CART_ITEM_NOT_FOUND));

        return new CartResponse(
                cart.getId(),
                userResp.id(),
                calculateCartItemTotalPrice(cart.getCartItems()),
                cart.getCartItems()
                        .stream()
                        .map(item -> new CartItemResponse(
                                        item.getId(),
                                        item.getProductId(),
                                        item.getProductUnitId(),
                                        extProductUnitResp.name(),
                                        item.getUnitPrice(),
                                        extProductUnitResp.images().get(0),
                                        extProductUnitResp.unitType(),
                                        item.getQuantity(),
                                        item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())),null
                                )
                        )
                        .toList(),
                null
        );
    }

    @Override
    public CartResponse getCartByUserId(Long id) {
        log.info("👤Getting the cart by user productUnitId {}", id);

        var userResp = userClient.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.USER_NOT_FOUND));

        var cart = cartRepository.findByUserId(userResp.id())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.CART_NOT_FOUND));

        var cartItems = cart.getCartItems()
                .stream()
                .filter(ci -> ci.getStatus() == CartItemStatus.ACTIVE)
                .sorted(Comparator.comparing(CartItem::getId))
                .toList();
        var productUnitIds = cart.getCartItems().stream().map(CartItem::getProductUnitId).toList();
        var productUnitBatch = productClient.getBatchProductUnitByIds(productUnitIds).orElseThrow();

        return new CartResponse(
                cart.getId(),
                userResp.id(),
                calculateCartItemTotalPrice(cartItems),
                cartItemResponseMapper(cartItems, productUnitBatch),
                null
        );
    }

    @Override
    public CartResponse convertCart(CartConvertRequest request) {

        var cart = cartRepository.findByUserId(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException(CartErrorInfo.USER_NOT_FOUND));

        var cartItems = cartItemRepository.findByIdInAndStatus(request.cartItemIds(), CartItemStatus.ACTIVE);

        if (cartItems.isEmpty()) {
            throw new InvalidCartRequestException(CartErrorInfo.CART_ITEM_NOT_ACTIVE);
        }

        cartItems
                .forEach(cartItem -> {
                        cartItem.setStatus(CartItemStatus.BEGIN_CHECKOUT);
                        cartItem.setConvertedAt(LocalDateTime.now());
                    }
                );

        var productUnitIds = cart.getCartItems().stream().map(CartItem::getProductUnitId).toList();
        var productUnitBatch = productClient.getBatchProductUnitByIds(productUnitIds).orElseThrow();

        var cartResponse = new CartResponse(
                cart.getId(),
                cart.getUserId(),
                calculateCartItemTotalPrice(cart.getCartItems()),
                cartItemResponseMapper(cartItems, productUnitBatch),
                CartReqStatus.CONVERSION_SUCCESS
        );

        cartRepository.save(cart);
        log.info("🔁Cart item with product productUnitId of [{}] successfully converted. Message: ",
                CartItemStatus.BEGIN_CHECKOUT.getDescription());
        return cartResponse;
    }


    private List<CartItemResponse> cartItemResponseMapper(
            List<CartItem> cartItems,
            List<ProductBatchResp> productBatchResponses
    ) {
        return cartItems
                .stream()
                .map(cartItem -> {
                    var productBatchResp = productBatchResponses
                            .stream()
                            .filter(item ->
                                    item.productUnitId().equals(cartItem.getProductUnitId())
                            )
                            .findFirst()
                            .orElseThrow();

                    return new CartItemResponse(
                            cartItem.getId(),
                            cartItem.getProductId(),
                            cartItem.getProductUnitId(),
                            productBatchResp.name(),
                            cartItem.getUnitPrice(),
                            productBatchResp.images().get(0),
                            productBatchResp.unitType(),
                            cartItem.getQuantity(),
                            cartItem.getTotalPrice(),
                            cartItem.getStatus()
                    );
                })
                .collect(Collectors.toList());
    }


}
