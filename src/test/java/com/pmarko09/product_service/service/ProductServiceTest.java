package com.pmarko09.product_service.service;

import com.pmarko09.product_service.mapper.ProductMapper;
import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.*;
import com.pmarko09.product_service.repository.ProductRepository;
import com.pmarko09.product_service.repository.ProductSpecificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class ProductServiceTest {

    ProductRepository productRepository;
    ProductSpecificationRepository productSpecificationRepository;
    ProductMapper productMapper;
    ProductService productService;

    @BeforeEach
    void setup() {
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.productSpecificationRepository = Mockito.mock(ProductSpecificationRepository.class);
        this.productMapper = Mappers.getMapper(ProductMapper.class);
        this.productService = new ProductService(productRepository,
                productSpecificationRepository, productMapper);
    }

    @Test
    void getAllProducts_DataCorrect_ProductDtosReturned() {
        log.info("Start test: getAllProducts_DataCorrect_ProductDtosReturned");
        Pageable pageable = PageRequest.of(0, 10);
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("B");
        product2.setType(ProductType.SMARTPHONE);
        product2.setPrice(1.0);

        when(productRepository.findAllProducts(pageable)).thenReturn(List.of(product1, product2));

        List<ProductDto> result = productService.getAllProducts(pageable);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("A", result.getFirst().getName());
        assertEquals("COMPUTER", result.getFirst().getType());
        assertEquals(9.9, result.getFirst().getPrice());
        assertEquals(2L, result.get(1).getId());
        assertEquals("B", result.get(1).getName());
        assertEquals("SMARTPHONE", result.get(1).getType());
        assertEquals(1.0, result.get(1).getPrice());

        verify(productRepository).findAllProducts(pageable);
        log.info("Finish test: getAllProducts_DataCorrect_ProductDtosReturned");
    }

    @Test
    void getByType_DataCorrect_ProductDtosReturned() {
        log.info("Start test: getByType_DataCorrect_ProductDtosReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("B");
        product2.setType(ProductType.COMPUTER);
        product2.setPrice(1.0);

        when(productRepository.findByType(ProductType.COMPUTER)).thenReturn(List.of(product1, product2));

        List<ProductDto> result = productService.getByType(ProductType.COMPUTER);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("A", result.getFirst().getName());
        assertEquals("COMPUTER", result.getFirst().getType());
        assertEquals(9.9, result.getFirst().getPrice());
        assertEquals(2L, result.get(1).getId());
        assertEquals("B", result.get(1).getName());
        assertEquals("COMPUTER", result.get(1).getType());
        assertEquals(1.0, result.get(1).getPrice());

        verify(productRepository).findByType(ProductType.COMPUTER);
        log.info("Finish test: getByType_DataCorrect_ProductDtosReturned");
    }

    @Test
    void getProductById_DataCorrect_ProductDtoReturned() {
        log.info("Start test: getProductById_DataCorrect_ProductDtoReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        assertEquals("COMPUTER", result.getType());
        assertEquals(9.9, result.getPrice());

        verify(productRepository).findById(1L);
        log.info("Finish test: getProductById_DataCorrect_ProductDtoReturned");
    }

    @Test
    void getProductByName_DataCorrect_ProductDtoReturned() {
        log.info("Start test: getProductByName_DataCorrect_ProductDtoReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);

        when(productRepository.findByName("A")).thenReturn(Optional.of(product1));

        ProductDto result = productService.getProductByName("A");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        assertEquals("COMPUTER", result.getType());
        assertEquals(9.9, result.getPrice());

        verify(productRepository).findByName("A");
        log.info("Finish test: getProductByName_DataCorrect_ProductDtoReturned");
    }

    @Test
    void addProduct_DataCorrect_ProductDtoReturned() {
        log.info("Start test: addProduct_DataCorrect_ProductDtoReturned");
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);
        computerSpecification.setProcessor(processor);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);
        product1.setSpecification(computerSpecification);

        when(productRepository.save(any(Product.class))).thenReturn(product1);

        ProductDto result = productService.addProduct(product1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        assertEquals("COMPUTER", result.getType());
        assertEquals(9.9, result.getPrice());

        verify(productRepository).save(product1);
        log.info("Finish test: addProduct_DataCorrect_ProductDtoReturned");
    }

    @Test
    void editProduct_DataCorrect_ProductDtoReturned() {
        log.info("Start test: editProduct_DataCorrect_ProductDtoReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);
        Product updatedProduct1 = new Product();
        updatedProduct1.setId(1L);
        updatedProduct1.setName("AAA");
        updatedProduct1.setType(ProductType.COMPUTER);
        updatedProduct1.setPrice(9.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(product1)).thenReturn(updatedProduct1);

        ProductDto result = productService.editProduct(1L, updatedProduct1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AAA", result.getName());
        assertEquals("COMPUTER", result.getType());
        assertEquals(9.99, result.getPrice());

        verify(productRepository).save(product1);
        log.info("Finish test: editProduct_DataCorrect_ProductDtoReturned");
    }

    @Test
    void addProductSpecification_DataCorrect_ProductDtoReturned() {
        log.info("Start test: addProductSpecification_DataCorrect_ProductDtoReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);
        Ram ram = new Ram(1L, "123");
        Processor processor = new Processor(5L, "AB");
        ComputerSpecification computerSpecification = new ComputerSpecification();
        computerSpecification.setId(11L);
        computerSpecification.setMemorySize(ram);
        computerSpecification.setProcessor(processor);
        computerSpecification.setProduct(product1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productSpecificationRepository.findById(11L)).thenReturn(Optional.of(computerSpecification));
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        ProductDto result = productService.addProductSpecification(1L, 11L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getName());
        assertEquals("COMPUTER", result.getType());
        assertEquals(9.9, result.getPrice());
        assertEquals(11L, result.getSpecificationId());

        verify(productRepository).findById(1L);
        verify(productSpecificationRepository).findById(11L);
        verify(productRepository).save(product1);
        log.info("Finish test: addProductSpecification_DataCorrect_ProductDtoReturned");
    }

    @Test
    void deleteProduct_DataCorrect_ProductDtoReturned() {
        log.info("Start test: deleteProduct_DataCorrect_ProductDtoReturned");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("A");
        product1.setType(ProductType.COMPUTER);
        product1.setPrice(9.9);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product1);
        log.info("Finish test: deleteProduct_DataCorrect_ProductDtoReturned");
    }
}
