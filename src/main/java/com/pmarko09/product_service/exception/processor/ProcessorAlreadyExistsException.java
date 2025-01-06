package com.pmarko09.product_service.exception.processor;

public class ProcessorAlreadyExistsException extends RuntimeException {
    public ProcessorAlreadyExistsException(String message) {
        super(message);
    }
}
