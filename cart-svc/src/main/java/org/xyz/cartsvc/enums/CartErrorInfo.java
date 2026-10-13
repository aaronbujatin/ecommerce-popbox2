package org.xyz.cartsvc.enums;

import lombok.Getter;

@Getter
public enum CartErrorInfo {

   CART_NOT_FOUND("PR0404", "Cart not found"),
   USER_NOT_FOUND("PR0405", "User productUnitId not found"),
   PRODUCT_NOT_FOUND("PR0406", "Product productUnitId not found"),
   PRODUCT_UNIT_NOT_FOUND("PR0408", "Product unit productUnitId not found"),
   PRODUCT_OUT_OF_STOCK("PR0407", "Product out of stock"),
   PRODUCT_UNIT_OUT_OF_STOCK("PR0409", "Product unit out of stock"),
   CART_NOT_ACTIVE("PR0408", "Cart is not active"),
   CART_ITEM_NOT_FOUND("PR0411", "Cart item not found"),
   CART_ITEM_NOT_ACTIVE("PR0412", "Cart item was not active"),
   USER_CART_DOES_NOT_EXIST("CR0410", "User cart does not exist");

    private final String code;
    private final String message;

    CartErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }


}