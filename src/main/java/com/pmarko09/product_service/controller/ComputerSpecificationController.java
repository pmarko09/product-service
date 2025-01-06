package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.service.ComputerSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialization/computer")
@RequiredArgsConstructor
public class ComputerSpecificationController {

    private final ComputerSpecificationService service;

    @GetMapping
    public List<ComputerSpecificationDto> getAllComputerSpec() {
        return service.getAllComputerSpecializations();
    }

    @GetMapping("/{id}")
    public ComputerSpecificationDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ComputerSpecificationDto addComputerSpecification(@RequestBody ComputerSpecification computerSpecification) {
        return service.addComputerSpecification(computerSpecification);
    }

    @PostMapping("/{computerSpecId}/processor/{processorId}")
    public ComputerSpecificationDto assignProcessor(@PathVariable Long processorId, @PathVariable Long computerSpecId) {
        return service.assignProcessor(processorId, computerSpecId);
    }

    @PostMapping("/{computerSpecId}/ram/{ramId}")
    public ComputerSpecificationDto assignRam(@PathVariable Long ramId, @PathVariable Long computerSpecId) {
        return service.assignRam(ramId, computerSpecId);
    }

    @DeleteMapping("/{computerSpecId}")
    public void deleteComputerSpecialization(@PathVariable Long computerSpecId) {
        service.deleteComputerSpecification(computerSpecId);
    }

}
