package com.pmarko09.product_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComputerSpecificationDto implements ProductSpecificationDto {
    private Long id;
    private Long productId;
    private String processorName;
    private Integer ramSize;
}