package org.xyz.productsvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "PRODUCT_UNIT")
public class ProductUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductUnitType productUnitType;
    private BigDecimal price;
    private int stock;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
