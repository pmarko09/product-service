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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComputerSpecificationService {

    private final ProductSpecificationRepository repository;
    private final ComputerSpecificationMapper computerSpecificationMapper;
    private final ProcessorRepository processorRepository;
    private final RamRepository ramRepository;

    /**
     * Retrieves all computer specifications from the repository.
     *
     * @return List of ComputerSpecificationDto containing all computer specifications
     */
    public List<ComputerSpecificationDto> getAllComputerSpecifications() {
        log.info("ComputerSpecificationService: fetching all computer specifications");
        return repository.findAllComputerSpecifications().stream()
                .map(computerSpecificationMapper::toDto)
                .toList();
    }

    /**
     * Finds a specific computer specification by its unique identifier.
     *
     * @param id The unique identifier of the computer specification
     * @return ComputerSpecificationDto representing the found specification
     */
    public ComputerSpecificationDto getById(Long id) {
        log.info("ComputerSpecificationService: fetching computer specifications with ID: {}", id);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        return computerSpecificationMapper.toDto(computerSpecification);
    }

    /**
     * Adds a new computer specification to the repository.
     *
     * @param computerSpecification The ComputerSpecification entity to be added
     * @return ComputerSpecificationDto of the newly created specification
     */
    @Transactional
    public ComputerSpecificationDto addComputerSpecification(ComputerSpecification computerSpecification) {
        log.info("ComputerSpecificationService: adding computer specifications with details: {}", computerSpecification);
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    /**
     * Assigns a processor to an existing computer specification.
     *
     * @param processorId             The unique identifier of the processor to assign
     * @param computerSpecificationId The unique identifier of the computer specification
     * @return ComputerSpecificationDto with the updated processor
     */
    @Transactional
    public ComputerSpecificationDto assignProcessor(Long processorId, Long computerSpecificationId) {
        log.info("ComputerSpecificationService: assigning processor with ID: {} to computer specification with ID: {}",
                processorId, computerSpecificationId);
        Processor processor = ProcessorValidation.existCheck(processorRepository, processorId);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, computerSpecificationId);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        computerSpecification.setProcessor(processor);
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    /**
     * Assigns RAM to an existing computer specification.
     *
     * @param ramId                   The unique identifier of the RAM to assign
     * @param computerSpecificationId The unique identifier of the computer specification
     * @return ComputerSpecificationDto with the updated memory size
     */
    @Transactional
    public ComputerSpecificationDto assignRam(Long ramId, Long computerSpecificationId) {
        log.info("ComputerSpecificationService: assigning ram with ID: {} to computer specification with ID: {}",
                ramId, computerSpecificationId);
        Ram ram = RamValidation.existCheck(ramRepository, ramId);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, computerSpecificationId);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        computerSpecification.setMemorySize(ram);
        return computerSpecificationMapper.toDto(repository.save(computerSpecification));
    }

    /**
     * Deletes a computer specification from the repository.
     *
     * @param id The unique identifier of the computer specification to be deleted
     */
    @Transactional
    public void deleteComputerSpecification(Long id) {
        log.info("ComputerSpecificationService: deleting computer specification with ID: {}", id);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        ComputerSpecification computerSpecification = ProductSpecificationValidation.computerSpecificationCheck(productSpecification);

        repository.delete(computerSpecification);
    }
}