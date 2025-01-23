package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.AccessoryDto;
import com.pmarko09.product_service.model.entity.Accessory;
import com.pmarko09.product_service.service.AccessoryService;
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

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * REST controller for managing accessories in the online shop.
 * <p>
 * Provides endpoints for CRUD operations on accessories including
 * retrieval, creation, modification, and deletion.
 */
@Slf4j
@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
@Tag(name = "Accessory Management", description = "Endpoints for managing accessories")
public class AccessoryController {

    /**
     * Service for handling accessory-related business logic.
     */
    private final AccessoryService accessoryService;

    /**
     * Retrieves all accessories with pagination support.
     *
     * @param pageable Pagination and sorting information
     * @return List of accessories matching pagination criteria
     */
    @GetMapping
    @Operation(summary = "Get all accessories", description = "Retrieve a paginated list of accessories")
    @Parameters({
            @Parameter(name = "pageable", description = "Pagination information")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved accessories"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public List<AccessoryDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /accessories{}", pageable);
        log.info("Fetching all accessories");
        return accessoryService.getAllAccessories(pageable);
    }

    /**
     * Retrieves a specific accessory by its unique identifier.
     *
     * @param accessoryId Unique identifier of the accessory
     * @return Detailed information about the requested accessory
     */
    @GetMapping("/{accessoryId}")
    @Operation(summary = "Get accessory by ID", description = "Retrieve a specific accessory by its unique identifier")
    @Parameters({
            @Parameter(name = "accessoryId", description = "Unique identifier of the accessory", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved accessory"),
            @ApiResponse(responseCode = "404", description = "Accessory not found")
    })
    public AccessoryDto findById(@PathVariable Long accessoryId) {
        log.info("Endpoint GET called: /accessories/{}", accessoryId);
        log.info("Fetching accessory by ID: {}", accessoryId);
        return accessoryService.findAccessoryById(accessoryId);
    }

    /**
     * Creates a new accessory in the system.
     *
     * @param accessory Accessory details to be added
     * @return The newly created accessory with system-generated details
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new accessory", description = "Create a new accessory in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accessory successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid accessory data")
    })
    public AccessoryDto add(@RequestBody Accessory accessory) {
        log.info("Endpoint POST called: /accessories");
        log.info("Adding accessory with details: {}", accessory);
        return accessoryService.addAccessory(accessory);
    }

    /**
     * Updates an existing accessory's details.
     *
     * @param id              Unique identifier of the accessory to update
     * @param editedAccessory Updated accessory information
     * @return The modified accessory with updated details
     */
    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit an existing accessory", description = "Update details of an existing accessory")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the accessory to edit", required = true),
            @Parameter(name = "editedAccessory", description = "Updated accessory details", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accessory successfully updated"),
            @ApiResponse(responseCode = "404", description = "Accessory not found"),
            @ApiResponse(responseCode = "400", description = "Invalid accessory data")
    })
    public AccessoryDto edit(@PathVariable Long id, @RequestBody Accessory editedAccessory) {
        log.info("Endpoint PUT called: /accessories/edit/{}", id);
        log.info("Editing accessory with ID: {} for details: {}", id, editedAccessory);
        return accessoryService.editAccessory(id, editedAccessory);
    }

    /**
     * Removes an accessory from the system.
     *
     * @param accessoryId Unique identifier of the accessory to delete
     */
    @DeleteMapping("/delete/{accessoryId}")
    @Operation(summary = "Delete an accessory", description = "Remove an accessory from the system")
    @Parameters({
            @Parameter(name = "accessoryId", description = "Unique identifier of the accessory to delete", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accessory successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Accessory not found")
    })
    public void delete(@PathVariable Long accessoryId) {
        log.info("Endpoint DELETE called: /accessories/delete/{}", accessoryId);
        log.info("Deleting accessory with ID: {}", accessoryId);
        accessoryService.deleteAccessory(accessoryId);
    }
}