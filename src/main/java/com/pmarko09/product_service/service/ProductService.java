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

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSpecificationRepository productSpecificationRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Pageable pageable) {
        log.info("ProductService: fetching all products");
        return productRepository.findAllProducts(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDto> getByType(ProductType type) {
        log.info("ProductService: fetching products by type: {}", type);
        ProductValidation.productCategoryCheck(type);
        return productRepository.findByType(type).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("ProductService: fetching product by ID: {}", id);
        Product product = ProductValidation.productExists(productRepository, id);
        return productMapper.toDto(product);
    }

    public ProductDto getProductByName(String productName) {
        log.info("ProductService: fetching product by name: {}", productName);
        Product product = ProductValidation.productNameExistCheck(productRepository, productName);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto addProduct(Product newProduct) {
        log.info("ProductService: adding product with details: {}", newProduct);
        ProductValidation.productNameCheck(newProduct);
        ProductValidation.productCategoryCheck(newProduct.getType());
        ProductValidation.productNameInUseCheck(productRepository, newProduct);
        ProductValidation.specificationElectronicsCheck(newProduct);

        return productMapper.toDto(productRepository.save(newProduct));
    }

    @Transactional
    public ProductDto editProduct(Long id, Product editedProduct) {
        log.info("ProductService: editing product with ID: {} and details: {}", id, editedProduct);
        Product product = ProductValidation.productExists(productRepository, id);
        ProductValidation.productNameInUseCheck(productRepository, editedProduct);
        Product.update(product, editedProduct);
        return productMapper.toDto(productRepository.save(product));
    }

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

    @Transactional
    public void deleteProduct(Long id) {
        log.info("ProductService: deleting product with ID: {}", id);
        Product product = ProductValidation.productExists(productRepository, id);
        productRepository.delete(product);
    }
}