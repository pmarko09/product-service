package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.model.entity.Ram;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ComputerSpecificationMapperTest {

    ComputerSpecificationMapper mapper = Mappers.getMapper(ComputerSpecificationMapper.class);

    @Test
    void mapComputerSpecificationToDto() {
        log.info("Start test: mapComputerSpecificationToDto");
        Processor processor = new Processor(1L, "x");
        Ram ram = new Ram(1L, "123");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(1L);
        computerSpecification.setProcessor(processor);
        computerSpecification.setMemorySize(ram);

        ComputerSpecificationDto result = mapper.toDto(computerSpecification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("x", result.getProcessorName());
        assertEquals("123", result.getRamSize().toString());
        log.info("Finish test: mapComputerSpecificationToDto");
    }
}

