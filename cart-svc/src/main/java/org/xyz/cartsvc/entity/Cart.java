package org.xyz.cartsvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "cartItems")
@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @SequenceGenerator(
            name = "cart_seq",
            sequenceName = "cart_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "cart_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private Long userId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(CartItem cartItem) {
        if (!this.cartItems.contains(cartItem)) {
            this.cartItems.add(cartItem);
            cartItem.setCart(this);
        }
    }

    public void removeToCart(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
    }

}
