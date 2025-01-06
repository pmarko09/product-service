package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @GetMapping
    public List<AccessoryDto> getAll(Pageable pageable) {
        return accessoryService.getAllAccessories(pageable);
    }

    @GetMapping("/{accessoryId}")
    public AccessoryDto findById(@PathVariable Long accessoryId) {
        return accessoryService.findAccessoryById(accessoryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccessoryDto add(@RequestBody Accessory accessory) {
        return accessoryService.addAccessory(accessory);
    }

    @PutMapping("/edit/{id}")
    public AccessoryDto edit(@PathVariable Long id, @RequestBody Accessory editedAccessory) {
        return accessoryService.editAccessory(id, editedAccessory);
    }

    @DeleteMapping("/delete/{accessoryId}")
    public void delete(@PathVariable Long accessoryId) {
        accessoryService.deleteAccessory(accessoryId);
    }
}
