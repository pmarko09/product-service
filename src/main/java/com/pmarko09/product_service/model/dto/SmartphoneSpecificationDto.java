package com.pmarko09.product_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartphoneSpecificationDto implements ProductSpecificationDto {
    private Long id;
    private Long productId;
    private String color;
    private Integer batteryCapacity;
    private Set<String> accessoriesNames;
}
