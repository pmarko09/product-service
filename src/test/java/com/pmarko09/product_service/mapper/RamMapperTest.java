package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class RamMapperTest {

    RamMapper ramMapper = Mappers.getMapper(RamMapper.class);

    @Test
    void mapRamToDto() {
        log.info("Start test: mapRamToDto");
        Ram ram = new Ram(1L, "123");

        RamDto result = ramMapper.toDto(ram);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("123", result.getSize());
        log.info("Finish test: mapRamToDto");
    }

}
