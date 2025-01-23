package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.RamDto;
import com.pmarko09.product_service.model.entity.Ram;
import com.pmarko09.product_service.service.RamService;
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
 * REST controller for managing RAM-related operations.
 * <p>
 * Provides endpoints for retrieving, creating, updating,
 * and deleting RAM information.
 */
@Slf4j
@RestController
@RequestMapping("/rams")
@RequiredArgsConstructor
@Tag(name = "RAM Management", description = "Endpoints for RAM operations")
public class RamController {

    /**
     * Service for handling RAM-related business logic.
     */
    private final RamService ramService;

    /**
     * Retrieves all RAM modules with pagination support.
     *
     * @param pageable Pagination and sorting information
     * @return List of RAM modules matching pagination criteria
     */
    @GetMapping
    @Operation(summary = "Get all RAM modules", description = "Retrieve paginated list of RAM modules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved RAM modules"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public List<RamDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /rams");
        log.info("Fetching all rams");
        return ramService.getAllRams(pageable);
    }

    /**
     * Creates a new RAM module in the system.
     *
     * @param ram RAM details to be added
     * @return The newly created RAM module
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new RAM module", description = "Create a new RAM module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "RAM module successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid RAM module data")
    })
    public RamDto addRam(@RequestBody Ram ram) {
        log.info("Endpoint POST called: /rams");
        log.info("Adding ram with details: {}", ram);
        return ramService.addRam(ram);
    }

    /**
     * Updates an existing RAM module's details.
     *
     * @param id         Unique identifier of the RAM module to update
     * @param updatedRam Updated RAM module information
     * @return The modified RAM module with updated details
     */
    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit an existing RAM module", description = "Update details of a RAM module")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the RAM module to edit", required = true),
            @Parameter(name = "updatedRam", description = "Updated RAM module details", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RAM module successfully updated"),
            @ApiResponse(responseCode = "404", description = "RAM module not found"),
            @ApiResponse(responseCode = "400", description = "Invalid RAM module data")
    })
    public RamDto editRam(@PathVariable Long id, @RequestBody Ram updatedRam) {
        log.info("Endpoint PUT called: /rams/edit/{}", id);
        log.info("Editing ram with ID: {} with details: {}", id, updatedRam);
        return ramService.editRam(id, updatedRam);
    }

    /**
     * Removes a RAM module from the system.
     *
     * @param id Unique identifier of the RAM module to delete
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a RAM module", description = "Remove a RAM module from the system")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the RAM module to delete", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RAM module successfully deleted"),
            @ApiResponse(responseCode = "404", description = "RAM module not found")
    })
    public void deleteRam(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /rams/delete/{}", id);
        log.info("Deleting ram with ID: {}", id);
        ramService.deleteRam(id);
    }
}