package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.service.SmartphoneSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialization/smartphone")
@RequiredArgsConstructor
public class SmartphoneSpecificationController {

    private final SmartphoneSpecificationService service;

    @GetMapping
    public List<SmartphoneSpecificationDto> getAllSmartphoneSpec() {
        return service.getAllSmartphones();
    }

    @GetMapping("/{id}")
    public SmartphoneSpecificationDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmartphoneSpecificationDto addSmartphoneSpecification(@RequestBody SmartphoneSpecification smartphoneSpecification) {
        return service.addSmartphoneSpecification(smartphoneSpecification);
    }

    @PostMapping("/{smartphoneSpecId}/accessory/{accessoryId}")
    public SmartphoneSpecificationDto assignAccessory(@PathVariable Long smartphoneSpecId, @PathVariable Long accessoryId) {
        return service.addAccessory(smartphoneSpecId, accessoryId);
    }

    @DeleteMapping("/{smartphoneSpecId}")
    public void deleteSmartphoneSpecialization(@PathVariable Long smartphoneSpecId) {
        service.deleteSmartphoneSpecification(smartphoneSpecId);
    }
}