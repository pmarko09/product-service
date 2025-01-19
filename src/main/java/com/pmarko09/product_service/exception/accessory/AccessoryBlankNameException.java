package com.pmarko09.product_service.exception.accessory;

public class AccessoryBlankNameException extends RuntimeException {
    public AccessoryBlankNameException() {
        super("Accessory name can not be blank");
    }
}
