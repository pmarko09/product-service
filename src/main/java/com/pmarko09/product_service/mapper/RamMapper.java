package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RamMapper {

    RamDto toDto(Ram ram);
}
