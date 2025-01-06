package com.pmarko09.product_service.exception.product;

public class InvalidSpecificationException extends RuntimeException {
    public InvalidSpecificationException(String message) {
        super(message);
    }
}
