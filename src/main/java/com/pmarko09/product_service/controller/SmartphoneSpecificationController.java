package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.service.SmartphoneSpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/specialization/smartphone")
@RequiredArgsConstructor
public class SmartphoneSpecificationController {

    private final SmartphoneSpecificationService service;

    @GetMapping
    public List<SmartphoneSpecificationDto> getAllSmartphoneSpec() {
        log.info("Endpoint GET called: /specialization/smartphone");
        log.info("Fetching all smartphone specifications");
        return service.getAllSmartphones();
    }

    @GetMapping("/{id}")
    public SmartphoneSpecificationDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /specialization/smartphone/{}", id);
        log.info("Fetching smartphone specification with ID: {}", id);
        return service.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmartphoneSpecificationDto addSmartphoneSpecification(@RequestBody SmartphoneSpecification smartphoneSpecification) {
        log.info("Endpoint POST called: /specialization/smartphone");
        log.info("Adding smartphone specification with details: {}", smartphoneSpecification);
        return service.addSmartphoneSpecification(smartphoneSpecification);
    }

    @PostMapping("/{smartphoneSpecId}/accessory/{accessoryId}")
    public SmartphoneSpecificationDto assignAccessory(@PathVariable Long smartphoneSpecId, @PathVariable Long accessoryId) {
        log.info("Endpoint POST called: /specialization/smartphone/{}/accessory/{}", smartphoneSpecId, accessoryId);
        log.info("Assigning accessory with ID: {} to smartphone specification with ID: {}", accessoryId, smartphoneSpecId);
        return service.addAccessory(smartphoneSpecId, accessoryId);
    }

    @DeleteMapping("/{smartphoneSpecId}")
    public void deleteSmartphoneSpecialization(@PathVariable Long smartphoneSpecId) {
        log.info("Endpoint DELETE called: /specialization/smartphone/{}", smartphoneSpecId);
        log.info("Deleting smartphone specification with ID: {}", smartphoneSpecId);
        service.deleteSmartphoneSpecification(smartphoneSpecId);
    }
}