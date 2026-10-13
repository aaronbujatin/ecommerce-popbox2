package org.xyz.cartsvc.exception;

import lombok.Getter;
import org.xyz.cartsvc.enums.CartErrorInfo;

@Getter
public class InvalidCartRequestException extends RuntimeException{

    private final CartErrorInfo cartErrorInfo;

    public InvalidCartRequestException(CartErrorInfo cartErrorInfo) {
        super(cartErrorInfo.getMessage());
        this.cartErrorInfo = cartErrorInfo;
    }



}
