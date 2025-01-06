package com.pmarko09.product_service.exception.productSpecification;

public class ProductSpecificationNotFound extends RuntimeException {
    public ProductSpecificationNotFound(String message) {
        super(message);
    }
}
