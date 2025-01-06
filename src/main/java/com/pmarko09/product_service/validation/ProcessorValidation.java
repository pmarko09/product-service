package com.pmarko09.product_service.validation;

import com.pmarko09.product_service.exception.processor.ProcessorAlreadyExistsException;
import com.pmarko09.product_service.exception.processor.ProcessorNotFound;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.repository.ProcessorRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessorValidation {

    public static void processorNameCheck(ProcessorRepository processorRepository, String name) {
        if (processorRepository.findByName(name).isPresent()) {
            throw new ProcessorAlreadyExistsException("Processor with name: " + name + " already exists");
        } else if (name == null) {
            throw new IllegalArgumentException("Processor name can not be null");
        }
    }

    public static Processor existCheck(ProcessorRepository processorRepository, Long id) {
        return processorRepository.findById(id)
                .orElseThrow(() -> new ProcessorNotFound("Processor with id: " + id + " not found"));
    }
}
