package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.service.RamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rams")
@RequiredArgsConstructor
public class RamController {

    private final RamService ramService;

    @GetMapping
    public List<RamDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /rams");
        log.info("Fetching all rams");
        return ramService.getAllRams(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RamDto addRam(@RequestBody Ram ram) {
        log.info("Endpoint POST called: /rams");
        log.info("Adding ram with details: {}", ram);
        return ramService.addRam(ram);
    }

    @PutMapping("/edit/{id}")
    public RamDto editRam(@PathVariable Long id, @RequestBody Ram updatedRam) {
        log.info("Endpoint PUT called: /rams/edit/{}", id);
        log.info("Editing ram with ID: {} with details: {}", id, updatedRam);
        return ramService.editRam(id, updatedRam);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRam(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /rams/delete/{}", id);
        log.info("Deleting ram with ID: {}", id);
        ramService.deleteRam(id);
    }
}