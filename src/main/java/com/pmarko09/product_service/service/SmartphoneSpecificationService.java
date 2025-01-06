package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ComputerSpecificationMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartphoneSpecificationService {

    private final ProductSpecificationRepository repository;
    private final SmartphoneSpecificationMapper smartphoneSpecificationMapper;
    private final AccessoryRepository accessoryRepository;

    public List<SmartphoneSpecificationDto> getAllSmartphones() {
        return repository.findAllSmartphoneSpecifications().stream()
                .map(smartphoneSpecificationMapper::toDto)
                .toList();
    }

    public SmartphoneSpecificationDto getById(Long id) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        return smartphoneSpecificationMapper.toDto(smartphoneSpecification);
    }

    @Transactional
    public SmartphoneSpecificationDto addSmartphoneSpecification(SmartphoneSpecification smartphoneSpecification) {
        ProductSpecificationValidation.smartphoneSpecificationDataCheck(smartphoneSpecification);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    @Transactional
    public SmartphoneSpecificationDto addAccessory(Long smartphoneSpecId, Long accessoryId) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, smartphoneSpecId);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);
        Accessory accessory = AccessoryValidation.accessoryExists(accessoryRepository, accessoryId);

        smartphoneSpecification.getAccessories().add(accessory);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    @Transactional
    public SmartphoneSpecificationDto editSmartphoneSpecification(Long smartphoneSpecId, SmartphoneSpecification updatedSpec) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, smartphoneSpecId);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        SmartphoneSpecification.update(smartphoneSpecification, updatedSpec);
        return smartphoneSpecificationMapper.toDto(repository.save(smartphoneSpecification));
    }

    @Transactional
    public void deleteSmartphoneSpecification(Long id) {
        ProductSpecification productSpecification = ProductSpecificationValidation.existCheck(repository, id);
        SmartphoneSpecification smartphoneSpecification = ProductSpecificationValidation.smartphoneSpecificationCheck(productSpecification);

        repository.delete(smartphoneSpecification);
    }
}
