package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ComputerSpecificationMapper;
import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.repository.ProcessorRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import com.pmarko09.product_service.repository.RamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class ComputerSpecificationServiceTest {

    ProductSpecificationRepository repository;
    ComputerSpecificationMapper computerSpecificationMapper;
    ProcessorRepository processorRepository;
    RamRepository ramRepository;
    ComputerSpecificationService computerSpecificationService;

    @BeforeEach
    void setup() {
        this.repository = Mockito.mock(ProductSpecificationRepository.class);
        this.computerSpecificationMapper = Mappers.getMapper(ComputerSpecificationMapper.class);
        this.processorRepository = Mockito.mock(ProcessorRepository.class);
        this.ramRepository = Mockito.mock(RamRepository.class);
        this.computerSpecificationService = new ComputerSpecificationService(repository,
                computerSpecificationMapper, processorRepository, ramRepository);
    }

    @Test
    void getAllComputerSpecializations_DataCorrect_ComputerSpecializationDtosReturned() {
        log.info("Start test: getAllComputerSpecializations_DataCorrect_ComputerSpecializationDtosReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);
        computerSpecification.setProcessor(processor);

        when(repository.findAllComputerSpecifications()).thenReturn(List.of(computerSpecification));

        List<ComputerSpecificationDto> result = computerSpecificationService.getAllComputerSpecifications();

        assertNotNull(result);
        assertEquals(11L, result.getFirst().getId());
        assertEquals("123", result.getFirst().getRamSize().toString());
        assertEquals("AB", result.getFirst().getProcessorName());

        verify(repository).findAllComputerSpecifications();
        log.info("Finish test: getAllComputerSpecializations_DataCorrect_ComputerSpecializationDtosReturned");
    }

    @Test
    void getById_DataCorrect_ComputerSpecializationDtoReturned() {
        log.info("Start test: getById_DataCorrect_ComputerSpecializationDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);
        computerSpecification.setProcessor(processor);

        when(repository.findById(11L)).thenReturn(Optional.of(computerSpecification));

        ComputerSpecificationDto result = computerSpecificationService.getById(11L);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        assertEquals("123", result.getRamSize().toString());
        assertEquals("AB", result.getProcessorName());

        verify(repository).findById(11L);
        log.info("Finish test: getById_DataCorrect_ComputerSpecializationDtoReturned");
    }

    @Test
    void addComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned() {
        log.info("Start test: addComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);
        computerSpecification.setProcessor(processor);

        when(repository.save(any(ComputerSpecification.class))).thenReturn(computerSpecification);

        ComputerSpecificationDto result = computerSpecificationService.addComputerSpecification(computerSpecification);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        assertEquals("123", result.getRamSize().toString());
        assertEquals("AB", result.getProcessorName());

        verify(repository).save(computerSpecification);
        log.info("Finish test: addComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned");
    }

    @Test
    void assignProcessor_DataCorrect_ComputerSpecializationDtoReturned() {
        log.info("Start test: assignProcessor_DataCorrect_ComputerSpecializationDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);

        when(processorRepository.findById(5L)).thenReturn(Optional.of(processor));
        when(repository.findById(11L)).thenReturn(Optional.of(computerSpecification));
        when(repository.save(any(ComputerSpecification.class))).thenReturn(computerSpecification);

        ComputerSpecificationDto result = computerSpecificationService.assignProcessor(5L, 11L);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        assertEquals("123", result.getRamSize().toString());
        assertEquals("AB", result.getProcessorName());

        verify(processorRepository).findById(5L);
        verify(repository).findById(11L);
        verify(repository).save(computerSpecification);
        log.info("Finish test: assignProcessor_DataCorrect_ComputerSpecializationDtoReturned");
    }

    @Test
    void assignRam_DataCorrect_ComputerSpecializationDtoReturned() {
        log.info("Start test: assignRam_DataCorrect_ComputerSpecializationDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setProcessor(processor);

        when(ramRepository.findById(1L)).thenReturn(Optional.of(ram));
        when(repository.findById(11L)).thenReturn(Optional.of(computerSpecification));
        when(repository.save(any(ComputerSpecification.class))).thenReturn(computerSpecification);

        ComputerSpecificationDto result = computerSpecificationService.assignRam(1L, 11L);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        assertEquals("123", result.getRamSize().toString());
        assertEquals("AB", result.getProcessorName());

        verify(repository).findById(11L);
        verify(ramRepository).findById(1L);
        verify(repository).save(computerSpecification);
        log.info("Finish test: assignRam_DataCorrect_ComputerSpecializationDtoReturned");
    }

    @Test
    void deleteComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned() {
        log.info("Start test: deleteComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setProcessor(processor);
        computerSpecification.setMemorySize(ram);

        when(repository.findById(11L)).thenReturn(Optional.of(computerSpecification));

        computerSpecificationService.deleteComputerSpecification(11L);

        verify(repository).delete(computerSpecification);
        log.info("Finish test: deleteComputerSpecification_DataCorrect_ComputerSpecializationDtoReturned");
    }
}


