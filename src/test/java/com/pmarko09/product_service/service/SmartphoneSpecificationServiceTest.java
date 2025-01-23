package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.SmartphoneSpecificationMapper;
import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.repository.AccessoryRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class SmartphoneSpecificationServiceTest {

    ProductSpecificationRepository repository;
    SmartphoneSpecificationMapper smartphoneSpecificationMapper;
    AccessoryRepository accessoryRepository;
    SmartphoneSpecificationService smartphoneSpecificationService;

    @BeforeEach
    void setup() {
        this.repository = Mockito.mock(ProductSpecificationRepository.class);
        this.smartphoneSpecificationMapper = Mappers.getMapper(SmartphoneSpecificationMapper.class);
        this.accessoryRepository = Mockito.mock(AccessoryRepository.class);
        this.smartphoneSpecificationService = new SmartphoneSpecificationService(repository,
                smartphoneSpecificationMapper, accessoryRepository);
    }

    @Test
    void getAllSmartphoneSpecifications_DataCorrect_SmartphoneDtosReturned() {
        log.info("Start test: getAllSmartphones_DataCorrect_SmartphoneDtosReturned");
        Accessory accessory = new Accessory(1L, "case");
        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("black");
        smartphoneSpecification.setBatteryCapacity(100);
        smartphoneSpecification.setAccessories(Set.of(accessory));

        when(repository.findAllSmartphoneSpecifications()).thenReturn(List.of(smartphoneSpecification));

        List<SmartphoneSpecificationDto> result = smartphoneSpecificationService.getAllSmartphoneSpecifications();

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("black", result.getFirst().getColor());
        assertEquals(100, result.getFirst().getBatteryCapacity());
        assertTrue(result.getFirst().getAccessoriesNames().contains("case"));

        verify(repository).findAllSmartphoneSpecifications();
        log.info("Finish test: getAllSmartphones_DataCorrect_SmartphoneDtosReturned");
    }

    @Test
    void getById_DataCorrect_SmartphoneDtoReturned() {
        log.info("Start test: getById_DataCorrect_SmartphoneDtoReturned");
        Accessory accessory = new Accessory(1L, "case");
        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("black");
        smartphoneSpecification.setBatteryCapacity(100);
        smartphoneSpecification.setAccessories(Set.of(accessory));

        when(repository.findById(1L)).thenReturn(Optional.of(smartphoneSpecification));

        SmartphoneSpecificationDto result = smartphoneSpecificationService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("black", result.getColor());
        assertEquals(100, result.getBatteryCapacity());
        assertTrue(result.getAccessoriesNames().contains("case"));

        verify(repository).findById(1L);
        log.info("Finish test: getById_DataCorrect_SmartphoneDtoReturned");
    }

    @Test
    void addSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned() {
        log.info("Start test: addSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("black");
        smartphoneSpecification.setBatteryCapacity(100);

        when(repository.save(any(SmartphoneSpecification.class))).thenReturn(smartphoneSpecification);

        SmartphoneSpecificationDto result = smartphoneSpecificationService.addSmartphoneSpecification(smartphoneSpecification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("black", result.getColor());
        assertEquals(100, result.getBatteryCapacity());

        verify(repository).save(smartphoneSpecification);
        log.info("Finish test: addSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
    }

    @Test
    void addAccessory_DataCorrect_SmartphoneDtoReturned() {
        log.info("Start test: addAccessory_DataCorrect_SmartphoneDtoReturned");
        Accessory accessory = new Accessory(10L, "case");
        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("black");
        smartphoneSpecification.setBatteryCapacity(100);

        when(repository.findById(1L)).thenReturn(Optional.of(smartphoneSpecification));
        when(accessoryRepository.findById(10L)).thenReturn(Optional.of(accessory));
        when(repository.save(any(SmartphoneSpecification.class))).thenReturn(smartphoneSpecification);

        SmartphoneSpecificationDto result = smartphoneSpecificationService.addAccessory(1L, 10L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("black", result.getColor());
        assertEquals(100, result.getBatteryCapacity());
        assertTrue(result.getAccessoriesNames().contains("case"));

        verify(repository).findById(1L);
        verify(accessoryRepository).findById(10L);
        log.info("Finish test: addAccessory_DataCorrect_SmartphoneDtoReturned");
    }

    @Test
    void editSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned() {
        log.info("Start test: editSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
        SmartphoneSpecification smartphoneSpecification1 = new SmartphoneSpecification();
        smartphoneSpecification1.setId(1L);
        smartphoneSpecification1.setColor("black");
        smartphoneSpecification1.setBatteryCapacity(100);

        SmartphoneSpecification updatedSmartphoneSpecification1 = new SmartphoneSpecification();
        updatedSmartphoneSpecification1.setId(1L);
        updatedSmartphoneSpecification1.setColor("red");
        updatedSmartphoneSpecification1.setBatteryCapacity(300);

        when(repository.findById(1L)).thenReturn(Optional.of(smartphoneSpecification1));
        when(repository.save(smartphoneSpecification1)).thenReturn(updatedSmartphoneSpecification1);

        SmartphoneSpecificationDto result = smartphoneSpecificationService.editSmartphoneSpecification(1L, updatedSmartphoneSpecification1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("red", result.getColor());
        assertEquals(300, result.getBatteryCapacity());

        verify(repository).findById(1L);
        verify(repository).save(smartphoneSpecification1);
        log.info("Finish test: editSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
    }

    @Test
    void deleteSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned() {
        log.info("Start test: deleteSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
        SmartphoneSpecification smartphoneSpecification1 = new SmartphoneSpecification();
        smartphoneSpecification1.setId(1L);
        smartphoneSpecification1.setColor("black");
        smartphoneSpecification1.setBatteryCapacity(100);

        when(repository.findById(1L)).thenReturn(Optional.of(smartphoneSpecification1));

        smartphoneSpecificationService.deleteSmartphoneSpecification(1L);

        verify(repository).delete(smartphoneSpecification1);
        log.info("Finish test: deleteSmartphoneSpecification_DataCorrect_SmartphoneDtoReturned");
    }
}