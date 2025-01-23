package com.pmarko09.product_service.validation;

import com.pmarko09.product_service.exception.productSpecification.IllegalProductSpecificationDataException;
import com.pmarko09.product_service.exception.productSpecification.ProductSpecificationNotFound;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.ProductSpecification;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecificationValidation {

    public static ProductSpecification existCheck(ProductSpecificationRepository repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductSpecificationNotFound("Product specification with id: " + id + " not found"));
    }

    public static ComputerSpecification computerSpecificationCheck(ProductSpecification productSpecification) {
        if (!(productSpecification instanceof ComputerSpecification computerSpecification)) {
            throw new UnsupportedOperationException("The specified product is not a computer specification");
        }
        return computerSpecification;
    }

    public static SmartphoneSpecification smartphoneSpecificationCheck(ProductSpecification productSpecification) {
        if (!(productSpecification instanceof SmartphoneSpecification smartphoneSpecification)) {
            throw new UnsupportedOperationException("The specified product specification is not a smartphone specification");
        }
        return smartphoneSpecification;
    }

    public static void smartphoneSpecificationDataCheck(SmartphoneSpecification smartphoneSpecification) {
        if (smartphoneSpecification.getColor() == null || smartphoneSpecification.getColor().isEmpty()) {
            throw new IllegalProductSpecificationDataException("Smartphone's color can not be null");
        } else if (smartphoneSpecification.getBatteryCapacity() == null || smartphoneSpecification.getBatteryCapacity().describeConstable().isEmpty()) {
            throw new IllegalProductSpecificationDataException("Smartphone's battery capacity can not be null");
        }
    }
}
