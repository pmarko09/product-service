package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @GetMapping
    public List<AccessoryDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /accessories{}", pageable);
        log.info("Fetching all accessories");
        return accessoryService.getAllAccessories(pageable);
    }

    @GetMapping("/{accessoryId}")
    public AccessoryDto findById(@PathVariable Long accessoryId) {
        log.info("Endpoint GET called: /accessories/{}", accessoryId);
        log.info("Fetching accessory by ID: {}", accessoryId);
        return accessoryService.findAccessoryById(accessoryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccessoryDto add(@RequestBody Accessory accessory) {
        log.info("Endpoint POST called: /accessories");
        log.info("Adding accessory with details: {}", accessory);
        return accessoryService.addAccessory(accessory);
    }

    @PutMapping("/edit/{id}")
    public AccessoryDto edit(@PathVariable Long id, @RequestBody Accessory editedAccessory) {
        log.info("Endpoint PUT called: /accessories/edit/{}", id);
        log.info("Editing accessory with ID: {} for details: {}", id, editedAccessory);
        return accessoryService.editAccessory(id, editedAccessory);
    }

    @DeleteMapping("/delete/{accessoryId}")
    public void delete(@PathVariable Long accessoryId) {
        log.info("Endpoint DELETE called: /accessories/delete/{}", accessoryId);
        log.info("Deleting accessory with ID: {}", accessoryId);
        accessoryService.deleteAccessory(accessoryId);
    }
}
