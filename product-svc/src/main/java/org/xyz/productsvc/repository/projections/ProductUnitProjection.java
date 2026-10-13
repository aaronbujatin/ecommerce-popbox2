package org.xyz.productsvc.repository.projections;

import org.xyz.productsvc.enums.ProductUnitType;

import java.math.BigDecimal;

public interface ProductUnitProjection {
    Long getId();

    BigDecimal getPrice();

    int getStockQty();

    ProductUnitType getType();
}