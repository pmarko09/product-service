package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import com.pmarko09.product_service.service.ProductService;
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
 * REST controller for managing product-related operations.
 * <p>
 * Provides endpoints for retrieving, creating, updating,
 * and deleting product information.
 */
@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Endpoints for product operations")
public class ProductController {

    /**
     * Service for handling product-related business logic.
     */
    private final ProductService productService;

    /**
     * Retrieves all products with pagination support.
     *
     * @param pageable Pagination and sorting information
     * @return List of products matching pagination criteria
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve paginated list of products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public List<ProductDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /products");
        log.info("Fetching all products");
        return productService.getAllProducts(pageable);
    }

    /**
     * Retrieves products by their specific type.
     *
     * @param type Product type to filter
     * @return List of products matching the specified type
     */
    @GetMapping("/by-type")
    @Operation(summary = "Get products by type", description = "Retrieve products of a specific type")
    @Parameters({
            @Parameter(name = "type", description = "Product type to filter", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
            @ApiResponse(responseCode = "400", description = "Invalid product type")
    })
    public List<ProductDto> getByType(@RequestParam ProductType type) {
        log.info("Endpoint GET called: /products/by-type");
        log.info("Fetching products by type: {}", type);
        return productService.getByType(type);
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id Unique identifier of the product
     * @return Detailed product information
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique identifier")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the product", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ProductDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /products/id/{}", id);
        log.info("Fetching product by ID: {}", id);
        return productService.getProductById(id);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name Name of the product
     * @return Detailed product information
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get product by name", description = "Retrieve a product by its name")
    @Parameters({
            @Parameter(name = "name", description = "Name of the product", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ProductDto getByName(@PathVariable String name) {
        log.info("Endpoint GET called: /products/name/{}", name);
        log.info("Fetching product by name: {}", name);
        return productService.getProductByName(name);
    }

    /**
     * Creates a new product in the system.
     *
     * @param newProduct Product details to be added
     * @return The newly created product
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add a new product", description = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ProductDto addProduct(@RequestBody Product newProduct) {
        log.info("Endpoint POST called: /products");
        log.info("Adding new product with details: {}", newProduct);
        return productService.addProduct(newProduct);
    }

    /**
     * Updates an existing product's details.
     *
     * @param id            Unique identifier of the product to update
     * @param editedProduct Updated product information
     * @return The modified product with updated details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Edit an existing product", description = "Update details of a product")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the product to edit", required = true),
            @Parameter(name = "editedProduct", description = "Updated product details", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ProductDto editProduct(@PathVariable Long id, @RequestBody Product editedProduct) {
        log.info("Endpoint PUT called: /products/{}", id);
        log.info("Editing product with ID: {} with details: {}", id, editedProduct);
        return productService.editProduct(id, editedProduct);
    }

    /**
     * Assigns a product specification to a specific product.
     *
     * @param productId              Unique identifier of the product
     * @param productSpecificationId Unique identifier of the product specification
     * @return Updated product with assigned specification
     */
    @PutMapping
    @Operation(summary = "Assign product specification", description = "Add a specification to a product")
    @Parameters({
            @Parameter(name = "productId", description = "Unique identifier of the product", required = true),
            @Parameter(name = "productSpecificationId", description = "Unique identifier of the product specification", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specification successfully assigned"),
            @ApiResponse(responseCode = "404", description = "Product or specification not found")
    })
    public ProductDto assignProductSpecification(@RequestParam Long productId, @RequestParam Long productSpecificationId) {
        log.info("Endpoint PUT called: /products");
        log.info("Assigning product specification with ID: {} to product with ID: {}", productSpecificationId, productId);
        return productService.addProductSpecification(productId, productSpecificationId);
    }

    /**
     * Removes a product from the system.
     *
     * @param id Unique identifier of the product to delete
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Remove a product from the system")
    @Parameters({
            @Parameter(name = "id", description = "Unique identifier of the product to delete", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public void deleteProduct(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /products/{}", id);
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
    }
}