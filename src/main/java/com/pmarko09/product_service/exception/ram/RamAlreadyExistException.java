package com.pmarko09.product_service.exception.ram;

public class RamAlreadyExistException extends RuntimeException {
    public RamAlreadyExistException(String message) {
        super(message);
    }
}
