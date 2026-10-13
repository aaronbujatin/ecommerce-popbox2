package org.xyz.productsvc.repository.projections;

import java.util.List;

public interface ProductProjection {
    Long getId();
    String getName();
    boolean getIsSeries();
    String getDescription();
    List<String> getImages();
    List<String> getImageContents();
    Long getCategoryId();
    String getProductUnits();
}
