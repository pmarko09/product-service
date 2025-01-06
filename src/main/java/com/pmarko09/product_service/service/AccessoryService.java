package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.AccessoryMapper;
import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.repository.AccessoryRepository;
import com.pmarko09.product_service.validation.AccessoryValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final AccessoryMapper accessoryMapper;

    public List<AccessoryDto> getAllAccessories(Pageable pageable) {
        return accessoryRepository.findAllAccessories(pageable).stream()
                .map(accessoryMapper::toDto)
                .toList();
    }

    public AccessoryDto findAccessoryById(Long id) {
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        return accessoryMapper.toDto(accessory);
    }

    @Transactional
    public AccessoryDto addAccessory(Accessory newAccessory) {
        AccessoryValidation.accessoryNameBlank(newAccessory);
        AccessoryValidation.accessoryName(accessoryRepository, newAccessory.getName());
        return accessoryMapper.toDto(accessoryRepository.save(newAccessory));
    }

    @Transactional
    public AccessoryDto editAccessory(Long id, Accessory editedAccessory) {
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        Accessory.update(accessory, editedAccessory);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Transactional
    public void deleteAccessory(Long id) {
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, id);
        accessoryRepository.delete(accessory);
    }
}