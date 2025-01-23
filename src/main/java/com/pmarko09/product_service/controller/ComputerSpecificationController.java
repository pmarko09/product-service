package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ComputerSpecificationDto;
import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.service.ComputerSpecificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing computer specifications in the online shop.
 * <p>
 * Provides endpoints for retrieving, creating, and managing
 * computer specification details, including processor and RAM assignments.
 */
@Slf4j
@RestController
@RequestMapping("/specification/computer")
@RequiredArgsConstructor
@Tag(name = "Computer Specification Management", description = "Endpoints for computer specification operations")
public class ComputerSpecificationController {

    /**
     * Service for handling computer specification-related business logic.
     */
    private final ComputerSpecificationService service;

    /**
     * Retrieves all computer specifications.
     *
     * @return List of all computer specifications
     */
    @GetMapping
    @Operation(summary = "Get all computer specifications", description = "Retrieve all computer specifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved specifications"),
            @ApiResponse(responseCode = "404", description = "No specifications found")
    })
    public List<ComputerSpecificationDto> getAllComputerSpec() {
        log.info("Endpoint GET called: /specification/computer");
        log.info("Fetching all computer specifications");
        return service.getAllComputerSpecifications();
    }

    /**
     * Retrieves a specific computer specification by its ID.
     *
     * @param id Unique identifier of the computer specification
     * @return Detailed computer specification
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get computer specification by ID", description = "Retrieve a specific computer specification")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the computer specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved specification"),
            @ApiResponse(responseCode = "404", description = "Specification not found")
    })
    public ComputerSpecificationDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /specification/computer/{}", id);
        log.info("Fetching computer specification with ID: {}", id);
        return service.getById(id);
    }

    /**
     * Creates a new computer specification.
     *
     * @param computerSpecification Computer specification details to be added
     * @return The newly created computer specification
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new computer specification", description = "Create a new computer specification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Specification successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid specification data")
    })
    public ComputerSpecificationDto addComputerSpecification(@RequestBody ComputerSpecification computerSpecification) {
        log.info("Endpoint POST called: /specification/computer");
        log.info("Adding computer specifications with details: {}", computerSpecification);
        return service.addComputerSpecification(computerSpecification);
    }

    /**
     * Assigns a processor to a computer specification.
     *
     * @param processorId    Unique identifier of the processor
     * @param computerSpecId Unique identifier of the computer specification
     * @return Updated computer specification with assigned processor
     */
    @PostMapping("/{computerSpecId}/processor/{processorId}")
    @Operation(summary = "Assign processor to specification", description = "Assign a processor to a computer specification")
    @Parameters({
            @Parameter(name = "processorId", description = "Unique identifier of the processor", required = true),
            @Parameter(name = "computerSpecId", description = "Unique identifier of the computer specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processor successfully assigned"),
            @ApiResponse(responseCode = "404", description = "Processor or specification not found")
    })
    public ComputerSpecificationDto assignProcessor(@PathVariable Long processorId, @PathVariable Long computerSpecId) {
        log.info("Endpoint POST called: /specification/computer/{}/processor/{}", computerSpecId, processorId);
        log.info("Assigning processor with ID: {} to computer specifications with ID: {}", processorId, computerSpecId);
        return service.assignProcessor(processorId, computerSpecId);
    }

    /**
     * Assigns RAM to a computer specification.
     *
     * @param ramId          Unique identifier of the RAM
     * @param computerSpecId Unique identifier of the computer specification
     * @return Updated computer specification with assigned RAM
     */
    @PostMapping("/{computerSpecId}/ram/{ramId}")
    @Operation(summary = "Assign RAM to specification", description = "Assign RAM to a computer specification")
    @Parameters({
            @Parameter(name = "ramId", description = "Unique identifier of the RAM", required = true),
            @Parameter(name = "computerSpecId", description = "Unique identifier of the computer specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RAM successfully assigned"),
            @ApiResponse(responseCode = "404", description = "RAM or specification not found")
    })
    public ComputerSpecificationDto assignRam(@PathVariable Long ramId, @PathVariable Long computerSpecId) {
        log.info("Endpoint POST called: /specifications/computer/{}/ram/{}", computerSpecId, ramId);
        log.info("Assigning ram with ID: {} to computer specifications with ID: {}", ramId, computerSpecId);
        return service.assignRam(ramId, computerSpecId);
    }

    /**
     * Deletes a computer specification.
     *
     * @param computerSpecId Unique identifier of the computer specification to delete
     */
    @DeleteMapping("/{computerSpecId}")
    @Operation(summary = "Delete computer specification", description = "Remove a computer specification from the system")
    @Parameters({
            @Parameter(name = "computerSpecId", description = "Unique identifier of the computer specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specification successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Specification not found")
    })
    public void deleteComputerSpecialization(@PathVariable Long computerSpecId) {
        log.info("Endpoint DELETE called: /specifications/computer/{}", computerSpecId);
        log.info("Deleting computer specification with ID: {}", computerSpecId);
        service.deleteComputerSpecification(computerSpecId);
    }
}