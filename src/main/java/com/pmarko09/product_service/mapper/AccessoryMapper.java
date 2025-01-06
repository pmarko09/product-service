package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessoryMapper {

    AccessoryDto toDto(Accessory entity);
}