package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processors")
@RequiredArgsConstructor
public class ProcessorController {

    private final ProcessorService processorService;

    @GetMapping
    public List<ProcessorDto> getAllProcessors(Pageable pageable) {
        return processorService.getAllProcessors(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProcessorDto addProcessor(@RequestBody Processor processor) {
        return processorService.addProcessor(processor);
    }

    @PutMapping("edit/{id}")
    public ProcessorDto editProcessor(@PathVariable Long id, @RequestBody Processor newProcessor) {
        return processorService.editProcessor(id, newProcessor);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProcessor(@PathVariable Long id) {
        processorService.deleteProcessor(id);
    }
}
