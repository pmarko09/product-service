package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class SmartphoneSpecificationMapperTest {

    SmartphoneSpecificationMapper mapper = Mappers.getMapper(SmartphoneSpecificationMapper.class);

    @Test
    void mapSmartphoneSpecificationToDto() {
        log.info("Start test: mapSmartphoneSpecificationToDto");
        SmartphoneSpecification smartphoneSpecification = new SmartphoneSpecification();
        smartphoneSpecification.setId(1L);
        smartphoneSpecification.setColor("red");
        smartphoneSpecification.setBatteryCapacity(1000);

        SmartphoneSpecificationDto result = mapper.toDto(smartphoneSpecification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("red", result.getColor());
        assertEquals(1000, result.getBatteryCapacity());
        log.info("Start test: mapSmartphoneSpecificationToDto");
    }
}
