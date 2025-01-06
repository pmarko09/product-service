package com.pmarko09.product_service.exception.product;

public class IllegalProductCategoryException extends RuntimeException {
    public IllegalProductCategoryException(String message) {
        super(message);
    }
}
