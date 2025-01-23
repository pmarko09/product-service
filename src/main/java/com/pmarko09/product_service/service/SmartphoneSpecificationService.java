package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.SmartphoneSpecificationMapper;
import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.model.entity.ProductSpecification;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.repository.AccessoryRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import com.pmarko09.product_service.validation.AccessoryValidation;
import com.pmarko09.product_service.validation.ProductSpecificationValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Smartphone Specification entities.
 * Handles operations related to managing smartphone specifications, such as retrieving, adding, editing, and deleting them.
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SmartphoneSpecificationService {

    private final ProductSpecificationRepository repository;
    private final SmartphoneSpecificationMapper smartphoneSpecificationMapper;
    private final AccessoryRepository accessoryRepository;

    /**
     * Retrieves all smartphone specifications.
     *
     * @return A list of SmartphoneSpecificationDto objects.
     */
    public List<SmartphoneSpecificationDto> getAllSmartphoneSpecifications() {
        log.info("SmartphoneSpecificationService: fetching all smartphone specifications");
        return repository.findAllSmartphoneSpecifications().stream()
                .map(smartphoneSpecificationMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a smartphone specification by its ID.
     *
     * @param id The ID of the smartphone specification.
     * @return A SmartphoneSpecificationDto object.
     */
    public SmartphoneSpecificationDto getById(Long id) {
        log.info("SmartphoneSpecificationService: fetching smartphone specification with ID: {}", id);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        return smartphoneSpecificationMapper.toDto(smartphoneSpecification);
    }

    /**
     * Adds a new smartphone specification.
     *
     * @param smartphoneSpecification The new smartphone specification to add.
     * @return A SmartphoneSpecificationDto object of the added smartphone specification.
     */
    @Transactional
    public SmartphoneSpecificationDto addSmartphoneSpecification(SmartphoneSpecification smartphoneSpecification) {
        log.info("SmartphoneSpecificationService: adding smartphone specification");
        ProductSpecificationValidation.smartphoneSpecificationDataCheck(smartphoneSpecification);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    /**
     * Adds an accessory to an existing smartphone specification.
     *
     * @param smartphoneSpecId The ID of the smartphone specification.
     * @param accessoryId      The ID of the accessory to add.
     * @return A SmartphoneSpecificationDto object of the updated smartphone specification.
     */
    @Transactional
    public SmartphoneSpecificationDto addAccessory(Long smartphoneSpecId, Long accessoryId) {
        log.info("SmartphoneSpecificationService: adding accessory with ID: {} to  smartphone specification with ID: {}",
                accessoryId, smartphoneSpecId);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, smartphoneSpecId);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, accessoryId);

        smartphoneSpecification.getAccessories().add(accessory);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    /**
     * Edits an existing smartphone specification.
     *
     * @param smartphoneSpecId The ID of the smartphone specification to edit.
     * @param updatedSpec       The updated smartphone specification data.
     * @return A SmartphoneSpecificationDto object of the edited smartphone specification.
     */
    @Transactional
    public SmartphoneSpecificationDto editSmartphoneSpecification(Long smartphoneSpecId, SmartphoneSpecification updatedSpec) {
        log.info("SmartphoneSpecificationService: editing smartphone specification with ID: {} with details: {}",
                smartphoneSpecId, updatedSpec);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, smartphoneSpecId);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        SmartphoneSpecification.update(smartphoneSpecification, updatedSpec);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    /**
     * Deletes a smartphone specification by its ID.
     *
     * @param id The ID of the smartphone specification to deleted.
     */
    @Transactional
    public void deleteSmartphoneSpecification(Long id) {
        log.info("SmartphoneSpecificationService: deleting smartphone specification with ID: {}", id);
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        repository.delete(smartphoneSpecification);
    }
}