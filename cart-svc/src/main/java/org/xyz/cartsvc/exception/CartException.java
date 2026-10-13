package org.xyz.cartsvc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.xyz.cartsvc.enums.CartErrorInfo;

@Getter
public abstract class CartException extends RuntimeException{

    private final CartErrorInfo cartErrorInfo;

    protected CartException(CartErrorInfo cartErrorInfo){
        super(cartErrorInfo.getMessage());
        this.cartErrorInfo = cartErrorInfo;
    }

    public abstract HttpStatus getHttpStatus();
}
