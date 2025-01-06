package com.pmarko09.product_service.exception.product;

public class ProductNameInUseException extends RuntimeException {
    public ProductNameInUseException(String message) {
        super(message);
    }
}
