package org.xyz.productsvc.exception;

import lombok.Getter;
import org.xyz.productsvc.enums.ProductErrorInfo;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private final ProductErrorInfo productErrorInfo;

    public ResourceNotFoundException(ProductErrorInfo productErrorInfo) {
        super(productErrorInfo.getMessage());
        this.productErrorInfo = productErrorInfo;
    }

}
