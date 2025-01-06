package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.service.RamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rams")
@RequiredArgsConstructor
public class RamController {

    private final RamService ramService;

    @GetMapping
    public List<RamDto> getAll(Pageable pageable) {
        return ramService.getAllRams(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RamDto addRam(@RequestBody Ram ram) {
        return ramService.addRam(ram);
    }

    @PutMapping("/edit/{id}")
    public RamDto editRam(@PathVariable Long id, @RequestBody Ram updatedRam) {
        return ramService.editRam(id, updatedRam);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRam(@PathVariable Long id) {
        ramService.deleteRam(id);
    }
}
