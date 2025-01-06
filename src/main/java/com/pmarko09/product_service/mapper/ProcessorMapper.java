package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessorMapper {

    ProcessorDto toDto(Processor entity);
}