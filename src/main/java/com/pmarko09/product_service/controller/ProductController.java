package com.pmarko09.product_service.controller;

import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import com.pmarko09.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor()
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAll(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/by-type/")
    public List<ProductDto> getByType(@RequestParam ProductType type) {
        return productService.getByType(type);
    }

    @GetMapping("/id/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public ProductDto getByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody Product newProduct) {
        return productService.addProduct(newProduct);
    }

    @PutMapping("/{id}")
    public ProductDto editProduct(@PathVariable Long id, @RequestBody Product editedProduct) {
        return productService.editProduct(id, editedProduct);
    }

    @PutMapping
    public ProductDto assignProductSpecification(@RequestParam Long productId, @RequestParam Long productSpecId) {
        return productService.addProductSpecification(productId, productSpecId);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}