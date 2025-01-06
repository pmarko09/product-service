package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SmartphoneSpecificationMapper {

    @Mapping(source = "accessories", target = "accessoriesNames", qualifiedByName = "mapAccessoriesToNames")
    @Mapping(source = "product.id", target = "productId")
    SmartphoneSpecificationDto toDto(SmartphoneSpecification entity);

    @Named("mapAccessoriesToNames")
    default Set<String> mapAccessoriesToNames(Set<Accessory> accessories) {
        return Optional.ofNullable(accessories)
                .orElse(Collections.emptySet()).stream()
                .map(Accessory::getName)
                .collect(Collectors.toSet());
    }
}