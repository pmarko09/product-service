package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import com.pmarko09.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor()
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAll(Pageable pageable) {
        log.info("Endpoint GET called: /products");
        log.info("Fetching all products");
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/by-type/")
    public List<ProductDto> getByType(@RequestParam ProductType type) {
        log.info("Endpoint GET called: /products/by-type");
        log.info("Fetching products by type: {}", type);
        return productService.getByType(type);
    }

    @GetMapping("/id/{id}")
    public ProductDto getById(@PathVariable Long id) {
        log.info("Endpoint GET called: /products/id/{}", id);
        log.info("Fetching product by ID: {}", id);
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public ProductDto getByName(@PathVariable String name) {
        log.info("Endpoint GET called: /products/name/{}", name);
        log.info("Fetching product by name: {}", name);
        return productService.getProductByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto addProduct(@RequestBody Product newProduct) {
        log.info("Endpoint POST called: /products");
        log.info("Adding new product with details: {}", newProduct);
        return productService.addProduct(newProduct);
    }

    @PutMapping("/{id}")
    public ProductDto editProduct(@PathVariable Long id, @RequestBody Product editedProduct) {
        log.info("Endpoint PUT called: /products/{}", id);
        log.info("Editing product with ID: {} with details: {}", id, editedProduct);
        return productService.editProduct(id, editedProduct);
    }

    @PutMapping
    public ProductDto assignProductSpecification(@RequestParam Long productId, @RequestParam Long productSpecId) {
        log.info("Endpoint PUT called: /products");
        log.info("Assigning product specification with ID: {} to product with ID: {}", productSpecId, productId);
        return productService.addProductSpecification(productId, productSpecId);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        log.info("Endpoint DELETE called: /products/{}", id);
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
    }
}