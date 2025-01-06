package com.pmarko09.product_service.exception.accessory;

public class IllegalAccessoryNameException extends RuntimeException {
    public IllegalAccessoryNameException(String name) {
        super(String.format("Accessory with given name: %s already exists", name));
    }
}