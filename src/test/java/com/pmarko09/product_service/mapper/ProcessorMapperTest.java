package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProcessorMapperTest {

    ProcessorMapper processorMapper = Mappers.getMapper(ProcessorMapper.class);

    @Test
    void mapProcessorToDto() {
        log.info("Start test: mapProcessorToDto");
        Processor processor = new Processor(1L, "ABC");

        ProcessorDto result = processorMapper.toDto(processor);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC", result.getName());
        log.info("Finish test: mapProcessorToDto");

    }
}