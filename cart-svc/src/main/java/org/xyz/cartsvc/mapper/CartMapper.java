package org.xyz.cartsvc.mapper;

import org.springframework.stereotype.Service;
import org.xyz.cartsvc.dto.CartItemProduct;
import org.xyz.cartsvc.dto.CartItemResponse;
import org.xyz.cartsvc.entity.CartItem;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMapper {


//    public List<CartItemResponse> mapListToCartItemResponse(List<CartItem> cartItems) {
//        return cartItems.stream()
//                .map(item ->
//                        new CartItemResponse(
//                                new CartItemProduct(
//                                        item.getProductId(),
//                                        item
//                                )
//                                item.getProductId(),
//                                item.getQuantity(),
//                                item.getTotalPrice()
//                        )
//                )
//                .collect(Collectors.toList());
//    }



}
