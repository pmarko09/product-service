package com.pmarko09.product_service.exception.product;

public class BlankProductNameException extends RuntimeException {
    public BlankProductNameException(String message) {
        super(message);
    }
}
