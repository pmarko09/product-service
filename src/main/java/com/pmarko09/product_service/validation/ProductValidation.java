package com.pmarko09.product_service.validation;

import com.pmarko09.product_service.exception.processor.ProcessorNotFound;
import com.pmarko09.product_service.exception.product.*;
import com.pmarko09.product_service.exception.productSpecification.IllegalProductSpecificationDataException;
import com.pmarko09.product_service.model.entity.*;
import com.pmarko09.product_service.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductValidation {

    public static Product productExists(ProductRepository productRepository, Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProcessorNotFound("Product with id: " + id + " not found"));
    }

    public static void productNameCheck(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new BlankProductNameException("Product's name can not be null");
        }
    }

    public static void productNameInUseCheck(ProductRepository productRepository, Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new ProductNameInUseException("Product name already in use");
        }
    }

    public static Product productNameExistCheck(ProductRepository productRepository, String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product name not found"));
    }

    public static void productCategoryCheck(ProductType type) {
        List<ProductType> list = Arrays.stream(ProductType.values()).toList();
        if (!list.contains(type)) {
            throw new IllegalProductCategoryException("Invalid product category provided");
        }
    }

    public static void validateSpecificationTypeMatch(Product product, ProductSpecification specification) {
        boolean isValid = switch (product.getType()) {
            case COMPUTER -> specification instanceof ComputerSpecification;
            case SMARTPHONE -> specification instanceof SmartphoneSpecification;
            case ELECTRONICS -> false;
        };

        if (!isValid) {
            throw new IllegalProductSpecificationDataException(
                    "Selected specification type does not match product type: " + product.getType()
            );
        }
    }

    public static void specificationElectronicsCheck(Product product) {
        if (product.getType().equals(ProductType.ELECTRONICS)) {
            if (product.getSpecification() != null) {
                throw new InvalidSpecificationException("Cannot assign a specification for products of type ELECTRONICS");
            }
            product.setSpecification(null);
        }
    }
}


