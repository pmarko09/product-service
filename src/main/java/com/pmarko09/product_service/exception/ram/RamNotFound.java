package com.pmarko09.product_service.exception.ram;

public class RamNotFound extends RuntimeException {
    public RamNotFound(String message) {
        super(message);
    }
}
