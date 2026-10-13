package org.xyz.cartsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.cartsvc.entity.CartItem;
import org.xyz.cartsvc.enums.CartItemStatus;

import java.util.List;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByIdInAndStatus(Iterable<Long> ids, CartItemStatus cartItemStatus);

}
