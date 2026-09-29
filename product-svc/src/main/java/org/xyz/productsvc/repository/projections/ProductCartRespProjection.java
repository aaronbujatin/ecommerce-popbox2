package org.xyz.productsvc.repository.projections;

import java.math.BigDecimal;
import java.util.List;


public interface ProductCartRespProjection {
    Long getProductId();
    Long getId();
    String getName();
    BigDecimal getPrice();
    String getProductUnitType();
    Integer getStock();
    String getDescription();
    List<String> getImages();

}
