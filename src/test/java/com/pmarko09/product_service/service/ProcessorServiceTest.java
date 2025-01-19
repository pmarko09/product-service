package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ProcessorMapper;
import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.repository.ProcessorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class ProcessorServiceTest {

    ProcessorRepository processorRepository;
    ProcessorMapper processorMapper;
    ProcessorService processorService;

    @BeforeEach
    void setup() {
        this.processorRepository = Mockito.mock(ProcessorRepository.class);
        this.processorMapper = Mappers.getMapper(ProcessorMapper.class);
        this.processorService = new ProcessorService(processorRepository,
                processorMapper);
    }

    @Test
    void getAllProcessors_DataCorrect_ProcessorDtosReturned() {
        log.info("Start test: getAllProcessors_DataCorrect_ProcessorDtosReturned");
        Pageable pageable = PageRequest.of(0, 10);
        Processor processor1 = new Processor(1L, "A");
        Processor processor2 = new Processor(2L, "B");
        List<Processor> processorList = List.of(processor1, processor2);

        when(processorRepository.findAllProcessors(pageable)).thenReturn(processorList);

        List<ProcessorDto> result = processorService.getAllProcessors(pageable);

        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
        assertEquals("A", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("B", result.get(1).getName());

        verify(processorRepository).findAllProcessors(pageable);
        log.info("Finish test: getAllProcessors_DataCorrect_ProcessorDtosReturned");
    }

    @Test
    void addProcessor_DataCorrect_ProcessorDtoReturned() {
        log.info("Start test: addProcessor_DataCorrect_ProcessorDtoReturned");
        Processor processor1 = new Processor(1L, "A");

        when(processorRepository.save(any(Processor.class))).thenReturn(processor1);

        ProcessorDto result = processorService.addProcessor(processor1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());

        verify(processorRepository).save(processor1);
        log.info("Finish test: addProcessor_DataCorrect_ProcessorDtoReturned");
    }

    @Test
    void editProcessor_DataCorrect_ProcessorDtoReturned() {
        log.info("Start test: editProcessor_DataCorrect_ProcessorDtoReturned");
        Processor processor1 = new Processor(1L, "A");
        Processor updatedProcessor1 = new Processor(1L, "AAA");

        when(processorRepository.findById(1L)).thenReturn(Optional.of(processor1));
        when(processorRepository.save(processor1)).thenReturn(updatedProcessor1);

        ProcessorDto result = processorService.editProcessor(1L, updatedProcessor1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AAA", result.getName());

        verify(processorRepository).findById(1L);
        log.info("Finish test: editProcessor_DataCorrect_ProcessorDtoReturned");
    }

    @Test
    void deleteProcessor_DataCorrect_ProcessorDtoReturned() {
        log.info("Start test: deleteProcessor_DataCorrect_ProcessorDtoReturned");
        Processor processor1 = new Processor(1L, "A");

        when(processorRepository.findById(1L)).thenReturn(Optional.of(processor1));

        processorService.deleteProcessor(1L);

        verify(processorRepository).delete(processor1);
        log.info("Finish test: deleteProcessor_DataCorrect_ProcessorDtoReturned");
    }
}