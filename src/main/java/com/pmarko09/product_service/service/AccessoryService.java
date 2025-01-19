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

    public List<AccessoryDto> getAllAccessories(Pageable pageable) {
        log.info("AccessoryService: fetching all accessories");
        return accessoryRepository.findAllAccessories(pageable).stream()
                .map(accessoryMapper::toDto)
                .toList();
    }

    public AccessoryDto findAccessoryById(Long id) {
        log.info("AccessoryService: fetching accessory by ID: {}", id);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        return accessoryMapper.toDto(accessory);
    }

    @Transactional
    public AccessoryDto addAccessory(Accessory newAccessory) {
        log.info("AccessoryService: adding accessory with details: {}", newAccessory);
        AccessoryValidation.accessoryNameBlank(newAccessory);
        AccessoryValidation.accessoryName(accessoryRepository, newAccessory.getName());
        return accessoryMapper.toDto(accessoryRepository.save(newAccessory));
    }

    @Transactional
    public AccessoryDto editAccessory(Long id, Accessory editedAccessory) {
        log.info("AccessoryService: editing accessory with ID: {} and details: {}", id, editedAccessory);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        Accessory.update(accessory, editedAccessory);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Transactional
    public void deleteAccessory(Long id) {
        log.info("AccessoryService: delete accessory with ID: {}", id);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        accessoryRepository.delete(accessory);
    }
}