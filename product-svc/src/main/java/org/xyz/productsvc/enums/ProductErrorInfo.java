package org.xyz.productsvc.enums;

import lombok.Getter;

@Getter
public enum ProductErrorInfo {

    PRODUCT_NOT_FOUND("PR0404", "Product not found"),
    PRODUCT_UNIT_NOT_FOUND("PR0405", "Product unit not found"),
    //EXTERNAL
    CATEGORY_NOT_FOUND("CT0404", "Category not found");

    private final String code;
    private final String message;

    ProductErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
