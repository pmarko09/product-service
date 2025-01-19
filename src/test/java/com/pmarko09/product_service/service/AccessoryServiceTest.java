package com.pmarko09.product_service.service;

import com.pmarko09.product_service.exception.accessory.AccessoryBlankNameException;
import com.pmarko09.product_service.exception.accessory.AccessoryNotFoundException;
import com.pmarko09.product_service.exception.accessory.IllegalAccessoryNameException;
import com.pmarko09.product_service.mapper.AccessoryMapper;
import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.repository.AccessoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class AccessoryServiceTest {

    AccessoryRepository accessoryRepository;
    AccessoryMapper accessoryMapper;
    AccessoryService accessoryService;

    @BeforeEach
    void setup() {
        this.accessoryRepository = Mockito.mock(AccessoryRepository.class);
        this.accessoryMapper = Mappers.getMapper(AccessoryMapper.class);
        this.accessoryService = new AccessoryService(accessoryRepository, accessoryMapper);
    }


    @Test
    void getAllAccessories_DataCorrect_AccessoryDtosReturned() {
        log.info("Start test: getAllAccessories_DataCorrect_AccessoryDtosReturned");
        Pageable pageable = PageRequest.of(0, 10);

        Accessory accessory = new Accessory(1L, "A");
        Accessory accessory2 = new Accessory(2L, "B");
        List<Accessory> accessoryList = List.of(accessory, accessory2);

        when(accessoryRepository.findAllAccessories(pageable)).thenReturn(accessoryList);

        List<AccessoryDto> result = accessoryService.getAllAccessories(pageable);

        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
        assertEquals("A", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("B", result.get(1).getName());
        log.info("Finish test: getAllAccessories_DataCorrect_AccessoryDtosReturned");
    }

    @Test
    void findAccessoryById_DataCorrect_AccessoryDtoReturned() {
        log.info("Start test: findAccessoryById_DataCorrect_AccessoryDtoReturned");
        Accessory accessory = new Accessory(1L, "A");

        when(accessoryRepository.findById(1L)).thenReturn(Optional.of(accessory));

        AccessoryDto result = accessoryService.findAccessoryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        log.info("Finish test: findAccessoryById_DataCorrect_AccessoryDtoReturned");
    }

    @Test
    void findAccessoryById_WrongIdProvided_ExceptionThrown() {
        log.info("Start test: findAccessoryById_WrongIdProvided_ExceptionThrown()");
        when(accessoryRepository.findById(1L)).thenReturn(Optional.empty());

        AccessoryNotFoundException exception = assertThrows(AccessoryNotFoundException.class, () ->
                accessoryService.findAccessoryById(1L));
        assertEquals("Accessory with id: 1 not found", exception.getMessage());
        log.info("Finish test: findAccessoryById_WrongIdProvided_ExceptionThrown()");
    }

    @Test
    void addAccessory_DataCorrect_AccessoryDtoReturned() {
        log.info("Start test: addAccessory_DataCorrect_AccessoryDtoReturned");
        Accessory accessory = new Accessory(1L, "A");

        when(accessoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(accessoryRepository.save(any(Accessory.class))).thenReturn(accessory);

        AccessoryDto result = accessoryService.addAccessory(accessory);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        log.info("Finish test: addAccessory_DataCorrect_AccessoryDtoReturned");
    }

    @Test
    void addAccessory_AccessoryNameNull_BlankNameExceptionThrown() {
        log.info("Start test: addAccessory_AccessoryNameNull_BlankNameExceptionThrown");
        Accessory accessory = new Accessory();
        accessory.setId(1L);

        AccessoryBlankNameException exception = assertThrows(AccessoryBlankNameException.class, () ->
                accessoryService.addAccessory(accessory));

        assertEquals("Accessory name can not be blank", exception.getMessage());
        log.info("Finish test: addAccessory_AccessoryNameNull_BlankNameExceptionThrown");
    }

    @Test
    void addAccessory_AccessoryNameNull_IllegalAccessoryNameExceptionThrown() {
        log.info("Start test: addAccessory_AccessoryNameNull_IllegalAccessoryNameExceptionThrown");
        Accessory accessory = new Accessory(1L, "A");

        when(accessoryRepository.findByName("A")).thenThrow(new IllegalAccessoryNameException("A"));

        IllegalAccessoryNameException exception = assertThrows(IllegalAccessoryNameException.class, () ->
                accessoryService.addAccessory(accessory));

        assertEquals("Accessory with given name: A already exists", exception.getMessage());
        log.info("Finish test: addAccessory_AccessoryNameNull_IllegalAccessoryNameExceptionThrown");
    }

    @Test
    void editAccessory_DataCorrect_AccessoryDtoReturned() {
        log.info("Start test: editAccessory_DataCorrect_AccessoryDtoReturned");
        Accessory accessory = new Accessory(1L, "A");
        Accessory updatedDaccessory = new Accessory(1L, "AAA");

        when(accessoryRepository.findById(1L)).thenReturn(Optional.of(accessory));
        when(accessoryRepository.save(accessory)).thenReturn(updatedDaccessory);

        AccessoryDto result = accessoryService.editAccessory(1L, updatedDaccessory);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AAA", result.getName());
        log.info("Finish test: editAccessory_DataCorrect_AccessoryDtoReturned");
    }

    @Test
    void deleteAccessory_DataCorrect_Void() {
        log.info("Start test: deleteAccessory_DataCorrect_Void");
        Accessory accessory = new Accessory(1L, "A");
        when(accessoryRepository.findById(1L)).thenReturn(Optional.of(accessory));

        accessoryService.deleteAccessory(1L);

        verify(accessoryRepository).delete(accessory);
        log.info("Finish test: deleteAccessory_DataCorrect_Void");
    }

    @Test
    void deleteAccessory_AccessoryNotFound_ExceptionThrown() {

        when(accessoryRepository.findById(1L)).thenReturn(Optional.empty());

        AccessoryNotFoundException exception = assertThrows(AccessoryNotFoundException.class, () ->
                accessoryService.deleteAccessory(1L));

        assertEquals("Accessory with id: 1 not found", exception.getMessage());
    }
}