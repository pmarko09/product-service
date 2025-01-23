package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ProductMapper;
import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.*;
import com.pmarko09.product_service.repository.ProductRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import com.pmarko09.product_service.validation.ProductSpecificationValidation;
import com.pmarko09.product_service.validation.ProductValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Product entities.
 * Handles CRUD operations of products.
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSpecificationRepository productSpecificationRepository;
    private final ProductMapper productMapper;

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable Pagination information.
     * @return A list of ProductDto objects.
     */
    public List<ProductDto> getAllProducts(Pageable pageable) {
        log.info("ProductService: fetching all products");
        return productRepository.findAllProducts(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    /**
     * Retrieves products by type.
     *
     * @param type The type of the product.
     * @return A list of ProductDto objects.
     */
    public List<ProductDto> getByType(ProductType type) {
        log.info("ProductService: fetching products by type: {}", type);
        ProductValidation.productCategoryCheck(type);
        return productRepository.findByType(type).stream()
                .map(productMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return A ProductDto object.
     */
    public ProductDto getProductById(Long id) {
        log.info("ProductService: fetching product by ID: {}", id);
        Product product = ProductValidation.productExists(productRepository, id);
        return productMapper.toDto(product);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param productName The name of the product.
     * @return A ProductDto object.
     */
    public ProductDto getProductByName(String productName) {
        log.info("ProductService: fetching product by name: {}", productName);
        String normalizedProductName = ProductValidation.productNameNormalizer(productName);
        Product product = ProductValidation.productNameExistCheck(productRepository, normalizedProductName);
        return productMapper.toDto(product);
    }

    /**
     * Adds a new product.
     *
     * @param newProduct The new product to add.
     * @return A ProductDto object of the added product.
     */
    @Transactional
    public ProductDto addProduct(Product newProduct) {
        log.info("ProductService: adding product with details: {}", newProduct);
        ProductValidation.productNameCheck(newProduct);
        ProductValidation.productCategoryCheck(newProduct.getType());
        ProductValidation.productNameInUseCheck(productRepository, newProduct);
        ProductValidation.specificationElectronicsCheck(newProduct);

        return productMapper.toDto(productRepository.save(newProduct));
    }

    /**
     * Edits an existing product.
     *
     * @param id            The ID of the product to edit.
     * @param editedProduct The updated product data.
     * @return A ProductDto object of the edited product.
     */
    @Transactional
    public ProductDto editProduct(Long id, Product editedProduct) {
        log.info("ProductService: editing product with ID: {} and details: {}", id, editedProduct);
        Product product = ProductValidation.productExists(productRepository, id);
        ProductValidation.productNameInUseCheck(productRepository, editedProduct);
        Product.update(product, editedProduct);
        return productMapper.toDto(productRepository.save(product));
    }

    /**
     * Adds a specification to a product.
     *
     * @param productId              The ID of the product.
     * @param productSpecificationId The ID of the product specification.
     * @return A ProductDto object of the updated product.
     */
    @Transactional
    public ProductDto addProductSpecification(Long productId, Long productSpecificationId) {
        log.info("ProductService: assigning product specification with ID: {} to product with ID: {}", productSpecificationId, productId);
        Product product = ProductValidation.productExists(productRepository, productId);
        ProductSpecification productSpecification = ProductSpecificationValidation
                .existCheck(productSpecificationRepository, productSpecificationId);

        ProductValidation.noSpecificationForElectronicsCheck(product);
        ProductValidation.validateSpecificationTypeMatch(product, productSpecification);

        product.setSpecification(productSpecification);
        productSpecification.setProduct(product);
        return productMapper.toDto(productRepository.save(product));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.info("ProductService: deleting product with ID: {}", id);
        Product product = ProductValidation.productExists(productRepository, id);
        productRepository.delete(product);
    }
}