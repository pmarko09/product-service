package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.model.entity.Ram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ComputerSpecificationMapper {

    @Mapping(source = "processor", target = "processorName", qualifiedByName = "mapProcessorToName")
    @Mapping(source = "memorySize", target = "ramSize", qualifiedByName = "mapRamToSize")
    @Mapping(source = "product.id", target = "productId")
    ComputerSpecificationDto toDto(ComputerSpecification entity);

    @Named("mapProcessorToName")
    default String mapProcessorToName(Processor processor) {
        return processor != null ? processor.getName() : null;
    }

    @Named("mapRamToSize")
    default String  mapRamToSize(Ram ram) {
        return ram != null ? ram.getSize() : null;
    }
}