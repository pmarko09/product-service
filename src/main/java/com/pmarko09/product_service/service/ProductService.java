package com.pmarko09.product_service.service;

import com.pmarko09.product_service.exception.product.IllegalProductCategoryException;
import com.pmarko09.product_service.mapper.ProductMapper;
import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.*;
import com.pmarko09.product_service.repository.ProductRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import com.pmarko09.product_service.validation.ProductSpecificationValidation;
import com.pmarko09.product_service.validation.ProductValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSpecificationRepository productSpecificationRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDto> getByType(ProductType type) {
        ProductValidation.productCategoryCheck(type);
        return productRepository.findByType(type).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getProductById(Long id) {
        Product product = ProductValidation.productExists(productRepository, id);
        return productMapper.toDto(product);
    }

    public ProductDto getProductByName(String productName) {
        Product product = ProductValidation.productNameExistCheck(productRepository, productName);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto addProduct(Product newProduct) {
        ProductValidation.productNameCheck(newProduct);
        ProductValidation.productCategoryCheck(newProduct.getType());
        ProductValidation.productNameInUseCheck(productRepository, newProduct);
        ProductValidation.specificationElectronicsCheck(newProduct);

        return productMapper.toDto(productRepository.save(newProduct));
    }

    @Transactional
    public ProductDto editProduct(Long id, Product editedProduct) {
        Product product = ProductValidation.productExists(productRepository, id);
        ProductValidation.productNameInUseCheck(productRepository, editedProduct);
        Product.update(product, editedProduct);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto addProductSpecification(Long productId, Long productSpecificationId) {
        Product product = ProductValidation.productExists(productRepository, productId);
        ProductSpecification productSpecification = ProductSpecificationValidation
                .existCheck(productSpecificationRepository, productSpecificationId);

        if (product.getType().equals(ProductType.ELECTRONICS)) {
            throw new IllegalProductCategoryException("For electronics product there is no further specification");
        }

        ProductValidation.validateSpecificationTypeMatch(product, productSpecification);

        product.setSpecification(productSpecification);
        productSpecification.setProduct(product);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = ProductValidation.productExists(productRepository, id);
        productRepository.delete(product);
    }
}
