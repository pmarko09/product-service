package com.pmarko09.product_service.exception.accessory;

public class AccessoryNotFoundException extends RuntimeException {
    public AccessoryNotFoundException(Long id) {
        super(String.format("Accessory with id: %s not found", id));
    }
}
