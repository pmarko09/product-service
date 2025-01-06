package com.pmarko09.product_service.exception.productSpecification;

public class IllegalProductSpecificationDataException extends RuntimeException{
    public IllegalProductSpecificationDataException(String message) {
        super(message);
    }
}
