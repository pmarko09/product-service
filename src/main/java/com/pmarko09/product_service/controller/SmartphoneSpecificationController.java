package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.SmartphoneSpecificationDto;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import com.pmarko09.product_service.service.SmartphoneSpecificationService;
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
 * REST controller for managing smartphone specification operations.
 * <p>
 * Provides endpoints for retrieving, creating,
 * and managing smartphone specification details.
 */
@Slf4j
@RestController
@RequestMapping("/specification/smartphone")
@RequiredArgsConstructor
@Tag(name = "Smartphone Specification Management", description = "Endpoints for smartphone specification operations")
public class SmartphoneSpecificationController {

    /**
     * Service for handling smartphone specification-related business logic.
     */
    private final SmartphoneSpecificationService service;

    /**
     * Retrieves all smartphone specifications.
     *
     * @return List of all smartphone specifications
     */
    @GetMapping
    @Operation(summary = "Get all smartphone specifications", description = "Retrieve all smartphone specifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved specifications"),
            @ApiResponse(responseCode = "404", description = "No specifications found")
    })
    public List<SmartphoneSpecificationDto> getAllSmartphoneSpec() {
        log.info("Endpoint GET called: /specification/smartphone");
        log.info("Fetching all smartphone specifications");
        return service.getAllSmartphoneSpecifications();
    }

    /**
     * Retrieves a specific smartphone specification by its ID.
     *
     * @param id Unique identifier of the smartphone specification
     * @return Detailed smartphone specification
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get smartphone specification by ID", description = "Retrieve a specific smartphone specification")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the smartphone specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved specification"),
            @ApiResponse(responseCode = "404", description = "Specification not found")
    })
    public SmartphoneSpecificationDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /specification/smartphone/{}", id);
        log.info("Fetching smartphone specification with ID: {}", id);
        return service.getById(id);
    }

    /**
     * Creates a new smartphone specification.
     *
     * @param smartphoneSpecification Smartphone specification details to be added
     * @return The newly created smartphone specification
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new smartphone specification", description = "Create a new smartphone specification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Specification successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid specification data")
    })
    public SmartphoneSpecificationDto addSmartphoneSpecification(@RequestBody SmartphoneSpecification smartphoneSpecification) {
        log.info("Endpoint POST called: /specification/smartphone");
        log.info("Adding smartphone specification with details: {}", smartphoneSpecification);
        return service.addSmartphoneSpecification(smartphoneSpecification);
    }

    /**
     * Assigns an accessory to a smartphone specification.
     *
     * @param smartphoneSpecId Unique identifier of the smartphone specification
     * @param accessoryId      Unique identifier of the accessory
     * @return Updated smartphone specification with assigned accessory
     */
    @PostMapping("/{smartphoneSpecId}/accessory/{accessoryId}")
    @Operation(summary = "Assign accessory to specification", description = "Add an accessory to a smartphone specification")
    @Parameters({
            @Parameter(name = "smartphoneSpecId", description = "Unique identifier of the smartphone specification", required = true),
            @Parameter(name = "accessoryId", description = "Unique identifier of the accessory", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accessory successfully assigned"),
            @ApiResponse(responseCode = "404", description = "Specification or accessory not found")
    })
    public SmartphoneSpecificationDto assignAccessory(@PathVariable Long smartphoneSpecId, @PathVariable Long accessoryId) {
        log.info("Endpoint POST called: /specification/smartphone/{}/accessory/{}", smartphoneSpecId, accessoryId);
        log.info("Assigning accessory with ID: {} to smartphone specification with ID: {}", accessoryId, smartphoneSpecId);
        return service.addAccessory(smartphoneSpecId, accessoryId);
    }

    /**
     * Deletes a smartphone specification.
     *
     * @param smartphoneSpecId Unique identifier of the smartphone specification to delete
     */
    @DeleteMapping("/{smartphoneSpecId}")
    @Operation(summary = "Delete smartphone specification", description = "Remove a smartphone specification from the system")
    @Parameters({
            @Parameter(name = "smartphoneSpecId", description = "Unique identifier of the smartphone specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specification successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Specification not found")
    })
    public void deleteSmartphoneSpecialization(@PathVariable Long smartphoneSpecId) {
        log.info("Endpoint DELETE called: /specification/smartphone/{}", smartphoneSpecId);
        log.info("Deleting smartphone specification with ID: {}", smartphoneSpecId);
        service.deleteSmartphoneSpecification(smartphoneSpecId);
    }
}