package com.pmarko09.product_service.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pmarko09.product_service.exception.accessory.AccessoryBlankNameException;
import com.pmarko09.product_service.exception.accessory.AccessoryNotFoundException;
import com.pmarko09.product_service.exception.accessory.IllegalAccessoryNameException;
import com.pmarko09.product_service.exception.processor.ProcessorAlreadyExistsException;
import com.pmarko09.product_service.exception.processor.ProcessorNotFound;
import com.pmarko09.product_service.exception.product.*;
import com.pmarko09.product_service.exception.productSpecification.IllegalProductSpecificationDataException;
import com.pmarko09.product_service.exception.productSpecification.ProductSpecificationNotFound;
import com.pmarko09.product_service.exception.ram.RamAlreadyExistException;
import com.pmarko09.product_service.exception.ram.RamNotFound;
import com.pmarko09.product_service.model.dto.ErrorMessageDto;
import com.pmarko09.product_service.model.entity.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessoryNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleAccessoryNotFound(AccessoryNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalAccessoryNameException.class)
    protected ResponseEntity<ErrorMessageDto> handleIllegalAccessoryName(IllegalAccessoryNameException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RamNotFound.class)
    protected ResponseEntity<ErrorMessageDto> handleRamNotFound(RamNotFound ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RamAlreadyExistException.class)
    protected ResponseEntity<ErrorMessageDto> handleIllegalRamSize(RamAlreadyExistException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProcessorNotFound.class)
    protected ResponseEntity<ErrorMessageDto> handleProcessorNotFound(ProcessorNotFound ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProcessorAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessageDto> handleProcessorAlreadyExistsException(ProcessorAlreadyExistsException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductSpecificationNotFound.class)
    protected ResponseEntity<ErrorMessageDto> handleProductSpecNotFound(ProductSpecificationNotFound ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalProductSpecificationDataException.class)
    protected ResponseEntity<ErrorMessageDto> handleProductSpecificationDataException(IllegalProductSpecificationDataException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    protected ResponseEntity<ErrorMessageDto> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleProductNotFound(ProductNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlankProductNameException.class)
    protected ResponseEntity<ErrorMessageDto> handleBlankProductNameException(BlankProductNameException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNameInUseException.class)
    protected ResponseEntity<ErrorMessageDto> handleProductNameInUseException(ProductNameInUseException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalProductCategoryException.class)
    protected ResponseEntity<ErrorMessageDto> handleIllegalProductCategoryException(IllegalProductCategoryException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSpecificationException.class)
    protected ResponseEntity<ErrorMessageDto> handleInvalidSpecificationException(InvalidSpecificationException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message = "Invalid request format";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            if (invalidFormatEx.getTargetType() != null && invalidFormatEx.getTargetType().equals(ProductType.class)) {
                message = String.format(
                        "Invalid product type: '%s'. Allowed values are: %s",
                        invalidFormatEx.getValue(),
                        Arrays.toString(ProductType.values())
                );
            }
        }

        ErrorMessageDto bodyResponse = new ErrorMessageDto(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessoryBlankNameException.class)
    protected ResponseEntity<ErrorMessageDto> handleAccessoryBlankNameException(AccessoryBlankNameException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}