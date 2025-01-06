package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ComputerSpecificationMapper;
import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.*;
import com.pmarko09.product_service.repository.ProcessorRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import com.pmarko09.product_service.repository.RamRepository;
import com.pmarko09.product_service.validation.ProcessorValidation;
import com.pmarko09.product_service.validation.ProductSpecificationValidation;
import com.pmarko09.product_service.validation.RamValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ComputerSpecificationService {

    private final ProductSpecificationRepository repository;
    private final ComputerSpecificationMapper computerSpecificationMapper;
    private final ProcessorRepository processorRepository;
    private final RamRepository ramRepository;

    public List<ComputerSpecificationDto> getAllComputerSpecializations() {
        return repository.findAllComputerSpecifications().stream()
                .map(computerSpecificationMapper::toDto)
                .toList();
    }

    public ComputerSpecificationDto getById(Long id) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        return computerSpecificationMapper.toDto(computerSpecification);
    }

    @Transactional
    public ComputerSpecificationDto addComputerSpecification(ComputerSpecification computerSpecification) {
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    @Transactional
    public ComputerSpecificationDto assignProcessor(Long processorId, Long computerSpecificationId) {
        Processor processor = ProcessorValidation.existCheck(processorRepository, processorId);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, computerSpecificationId);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        computerSpecification.setProcessor(processor);
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    @Transactional
    public ComputerSpecificationDto assignRam(Long ramId, Long computerSpecificationId) {
        Ram ram = RamValidation.existCheck(ramRepository, ramId);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, computerSpecificationId);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        computerSpecification.setMemorySize(ram);
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    @Transactional
    public void deleteComputerSpecification(Long id) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        repository.delete(computerSpecification);
    }
}