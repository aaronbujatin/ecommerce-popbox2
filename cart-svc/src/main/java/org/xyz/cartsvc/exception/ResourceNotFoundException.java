package org.xyz.cartsvc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.xyz.cartsvc.enums.CartErrorInfo;

@Getter
public class ResourceNotFoundException extends CartException{

    public ResourceNotFoundException(CartErrorInfo productErrorInfo) {
        super(productErrorInfo);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}

