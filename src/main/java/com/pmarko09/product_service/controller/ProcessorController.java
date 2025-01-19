package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/processors")
@RequiredArgsConstructor
public class ProcessorController {

    private final ProcessorService processorService;

    @GetMapping
    public List<ProcessorDto> getAllProcessors(Pageable pageable) {
        log.info("Endpoint GET called: /processors");
        log.info("Fetching all processors");
        return processorService.getAllProcessors(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProcessorDto addProcessor(@RequestBody Processor processor) {
        log.info("Endpoint POST called: /processors");
        log.info("Adding processor with details: {}", processor);
        return processorService.addProcessor(processor);
    }

    @PutMapping("/edit/{id}")
    public ProcessorDto editProcessor(@PathVariable Long id, @RequestBody Processor newProcessor) {
        log.info("Endpoint PUT called: /processors");
        log.info("Editing processor with ID: {} with details: {}", id, newProcessor);
        return processorService.editProcessor(id, newProcessor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProcessor(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /processors");
        log.info("Deleting processor with ID: {}", id);
        processorService.deleteProcessor(id);
    }
}