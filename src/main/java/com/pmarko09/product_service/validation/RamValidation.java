package com.pmarko09.product_service.validation;

import com.pmarko09.product_service.exception.ram.RamAlreadyExistException;
import com.pmarko09.product_service.exception.ram.RamNotFound;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.repository.RamRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RamValidation {

    public static Ram existCheck(RamRepository ramRepository, Long id) {
        return ramRepository.findById(id).
                orElseThrow(() -> new RamNotFound("Ram not found wit id: " + id));
    }

    public static void ramSizeCheck(RamRepository ramRepository, String size) {
        if (ramRepository.findBySize(size).isPresent()) {
            throw new RamAlreadyExistException("Ram with size: " + size + " already added");
        }
        if (size == null) {
            throw new IllegalArgumentException("Ram size can not be null");
        }
    }
}