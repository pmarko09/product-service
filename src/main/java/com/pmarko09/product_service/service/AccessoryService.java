package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.AccessoryMapper;
import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.repository.AccessoryRepository;
import com.pmarko09.product_service.validation.AccessoryValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final AccessoryMapper accessoryMapper;

    /**
     * Retrieves all accessories with pagination support.
     *
     * @param pageable Pagination information for fetching accessories
     * @return List of AccessoryDto containing all accessories for the given page
     */
    public List<AccessoryDto> getAllAccessories(Pageable pageable) {
        log.info("AccessoryService: fetching all accessories");
        return accessoryRepository.findAllAccessories(pageable).stream()
                .map(accessoryMapper::toDto)
                .toList();
    }

    /**
     * Finds a specific accessory by its unique identifier.
     *
     * @param id The unique identifier of the accessory
     * @return AccessoryDto representing the found accessory
     */
    public AccessoryDto findAccessoryById(Long id) {
        log.info("AccessoryService: fetching accessory by ID: {}", id);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        return accessoryMapper.toDto(accessory);
    }

    /**
     * Adds a new accessory to the repository.
     *
     * @param newAccessory The Accessory entity to be added
     * @return AccessoryDto of the newly created accessory
     */
    @Transactional
    public AccessoryDto addAccessory(Accessory newAccessory) {
        log.info("AccessoryService: adding accessory with details: {}", newAccessory);
        AccessoryValidation.accessoryNameBlank(newAccessory);
        AccessoryValidation.accessoryName(accessoryRepository, newAccessory.getName());
        return accessoryMapper.toDto(accessoryRepository.save(newAccessory));
    }

    /**
     * Edits an existing accessory identified by its ID.
     *
     * @param id              The unique identifier of the accessory to be edited
     * @param editedAccessory The updated Accessory entity
     * @return AccessoryDto of the updated accessory
     */
    @Transactional
    public AccessoryDto editAccessory(Long id, Accessory editedAccessory) {
        log.info("AccessoryService: editing accessory with ID: {} and details: {}", id, editedAccessory);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        Accessory.update(accessory, editedAccessory);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    /**
     * Deletes an accessory from the repository.
     *
     * @param id The unique identifier of the accessory to be deleted
     */
    @Transactional
    public void deleteAccessory(Long id) {
        log.info("AccessoryService: delete accessory with ID: {}", id);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        accessoryRepository.delete(accessory);
    }
}