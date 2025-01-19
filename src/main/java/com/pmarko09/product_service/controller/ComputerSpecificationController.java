package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.service.ComputerSpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/specialization/computer")
@RequiredArgsConstructor
public class ComputerSpecificationController {

    private final ComputerSpecificationService service;

    @GetMapping
    public List<ComputerSpecificationDto> getAllComputerSpec() {
        log.info("Endpoint GET called: /specialization/computer");
        log.info("Fetching all computer specializations");
        return service.getAllComputerSpecializations();
    }

    @GetMapping("/{id}")
    public ComputerSpecificationDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /specialization/computer/{}", id);
        log.info("Fetching computer specialization with ID: {}", id);
        return service.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ComputerSpecificationDto addComputerSpecification(@RequestBody ComputerSpecification computerSpecification) {
        log.info("Endpoint POST called: /specialization/computer");
        log.info("Adding computer specialization with details: {}", computerSpecification);
        return service.addComputerSpecification(computerSpecification);
    }

    @PostMapping("/{computerSpecId}/processor/{processorId}")
    public ComputerSpecificationDto assignProcessor(@PathVariable Long processorId, @PathVariable Long computerSpecId) {
        log.info("Endpoint POST called: /specialization/computer/{}/processor/{}", computerSpecId, processorId);
        log.info("Assigning processor with ID: {} to computer specialization with ID: {}", processorId, computerSpecId);
        return service.assignProcessor(processorId, computerSpecId);
    }

    @PostMapping("/{computerSpecId}/ram/{ramId}")
    public ComputerSpecificationDto assignRam(@PathVariable Long ramId, @PathVariable Long computerSpecId) {
        log.info("Endpoint POST called: /specialization/computer/{}/ram/{}", computerSpecId, ramId);
        log.info("Assigning ram with ID: {} to computer specialization with ID: {}", ramId, computerSpecId);
        return service.assignRam(ramId, computerSpecId);
    }

    @DeleteMapping("/{computerSpecId}")
    public void deleteComputerSpecialization(@PathVariable Long computerSpecId) {
        log.info("Endpoint DELETE called: /specialization/computer/{}", computerSpecId);
        log.info("Deleting computer specialization with ID: {}", computerSpecId);
        service.deleteComputerSpecification(computerSpecId);
    }
}