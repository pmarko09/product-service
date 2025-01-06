package com.pmarko09.product_service.exception.processor;

public class ProcessorNotFound extends RuntimeException {
    public ProcessorNotFound(String message) {
        super(message);
    }
}
