package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductSpecification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "specification", target = "specificationId",qualifiedByName = "mapSpecificationToId")
    ProductDto toDto(Product entity);

    @Named("mapSpecificationToId")
    default Long mapSpecificationToId(ProductSpecification specification) {
        return specification != null ? specification.getId() : null;
    }
}
