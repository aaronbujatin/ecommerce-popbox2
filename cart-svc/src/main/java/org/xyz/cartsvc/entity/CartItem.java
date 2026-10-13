package org.xyz.cartsvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.xyz.cartsvc.enums.CartItemStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = "cart")
@Table(name = "CART_ITEM")
public class CartItem {

    @Id
    @SequenceGenerator(
            name = "cart_item_seq",
            sequenceName = "cart_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "cart_item_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Long productId;
    private Long productUnitId;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private CartItemStatus status;
    private LocalDateTime convertedAt;

}