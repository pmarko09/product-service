package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProcessorDto;
import com.pmarko09.product_service.model.entity.Processor;
import com.pmarko09.product_service.service.ProcessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing processor-related operations.
 * <p>
 * Provides endpoints for retrieving, creating, updating,
 * and deleting processor information.
 */
@Slf4j
@RestController
@RequestMapping("/processors")
@RequiredArgsConstructor
@Tag(name = "Processor Management", description = "Endpoints for processor operations")
public class ProcessorController {

    /**
     * Service for handling processor-related business logic.
     */
    private final ProcessorService processorService;

    /**
     * Retrieves all processors with pagination support.
     *
     * @param pageable Pagination and sorting information
     * @return List of processors matching pagination criteria
     */
    @GetMapping
    @Operation(summary = "Get all processors", description = "Retrieve paginated list of processors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved processors"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public List<ProcessorDto> getAllProcessors(Pageable pageable) {
        log.info("Endpoint GET called: /processors");
        log.info("Fetching all processors");
        return processorService.getAllProcessors(pageable);
    }

    /**
     * Creates a new processor in the system.
     *
     * @param processor Processor details to be added
     * @return The newly created processor
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new processor", description = "Create a new processor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Processor successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid processor data")
    })
    public ProcessorDto addProcessor(@RequestBody Processor processor) {
        log.info("Endpoint POST called: /processors");
        log.info("Adding processor with details: {}", processor);
        return processorService.addProcessor(processor);
    }

    /**
     * Updates an existing processor's details.
     *
     * @param id           Unique identifier of the processor to update
     * @param newProcessor Updated processor information
     * @return The modified processor with updated details
     */
    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit an existing processor", description = "Update details of a processor")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the processor to edit", required = true),
            @Parameter(name = "newProcessor", description = "Updated processor details", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processor successfully updated"),
            @ApiResponse(responseCode = "404", description = "Processor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid processor data")
    })
    public ProcessorDto editProcessor(@PathVariable Long id, @RequestBody Processor newProcessor) {
        log.info("Endpoint PUT called: /processors");
        log.info("Editing processor with ID: {} with details: {}", id, newProcessor);
        return processorService.editProcessor(id, newProcessor);
    }

    /**
     * Removes a processor from the system.
     *
     * @param id Unique identifier of the processor to delete
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a processor", description = "Remove a processor from the system")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the processor to delete", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processor successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Processor not found")
    })
    public void deleteProcessor(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /processors");
        log.info("Deleting processor with ID: {}", id);
        processorService.deleteProcessor(id);
    }
}