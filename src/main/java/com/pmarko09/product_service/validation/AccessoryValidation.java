package com.pmarko09.product_service.validation;

import com.pmarko09.product_service.exception.accessory.AccessoryBlankNameException;
import com.pmarko09.product_service.exception.accessory.AccessoryNotFoundException;
import com.pmarko09.product_service.exception.accessory.IllegalAccessoryNameException;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.repository.AccessoryRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessoryValidation {

    public static Accessory accessoryExists(AccessoryRepository accessoryRepository, Long id) {
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new AccessoryNotFoundException(id));
    }

    public static void accessoryName(AccessoryRepository accessoryRepository, String name) {
        if (accessoryRepository.findByName(name).isPresent()) {
            throw new IllegalAccessoryNameException(name);
        }
    }

    public static void accessoryNameBlank(Accessory accessory) {
        if (accessory.getName() == null || accessory.getName().isEmpty()) {
            throw new AccessoryBlankNameException();
        }
    }
}