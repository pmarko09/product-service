package com.pmarko09.product_service.mapper;


import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class AccessoryMapperTest {

    AccessoryMapper accessoryMapper = Mappers.getMapper(AccessoryMapper.class);

    @Test
    void mapAccessoryToDto() {
        log.info("Star test: mapAccessoryToDto");
        Accessory accessory = new Accessory(1L, "A");

        AccessoryDto result = accessoryMapper.toDto(accessory);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        log.info("Finish test: mapAccessoryToDto");
    }

}
