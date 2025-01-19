package com.pmarko09.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import com.pmarko09.product_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getAll_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);
        List<ProductDto> list = List.of(productDto);
        Pageable pageable = PageRequest.of(0, 10);

        when(productService.getAllProducts(pageable)).thenReturn(list);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("AB"))
                .andExpect(jsonPath("$[0].price").value(9.9))
                .andExpect(jsonPath("$[0].type").value("COMPUTER"))
                .andExpect(jsonPath("$[0].specificationId").value(10L));

        verify(productService).getAllProducts(pageable);
        log.info("Finish test: getAll_DataCorrect_ReturnStatus200");
    }

    @Test
    void getByType_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getByType_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);
        List<ProductDto> list = List.of(productDto);

        when(productService.getByType(ProductType.COMPUTER)).thenReturn(list);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/by-type/?type=COMPUTER"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("AB"))
                .andExpect(jsonPath("$[0].price").value(9.9))
                .andExpect(jsonPath("$[0].type").value("COMPUTER"))
                .andExpect(jsonPath("$[0].specificationId").value(10L));

        verify(productService).getByType(ProductType.COMPUTER);
        log.info("Finish test: getByType_DataCorrect_ReturnStatus200");
    }

    @Test
    void getById_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getById_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);

        when(productService.getProductById(1L)).thenReturn(productDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AB"))
                .andExpect(jsonPath("$.price").value(9.9))
                .andExpect(jsonPath("$.type").value("COMPUTER"))
                .andExpect(jsonPath("$.specificationId").value(10L));

        verify(productService).getProductById(1L);
        log.info("Finish test: getById_DataCorrect_ReturnStatus200");
    }

    @Test
    void getByName_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: getByName_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);

        when(productService.getProductByName("AB")).thenReturn(productDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/name/AB"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AB"))
                .andExpect(jsonPath("$.price").value(9.9))
                .andExpect(jsonPath("$.type").value("COMPUTER"))
                .andExpect(jsonPath("$.specificationId").value(10L));

        verify(productService).getProductByName("AB");
        log.info("Finish test: getByName_DataCorrect_ReturnStatus200");
    }

    @Test
    void addProduct_DataCorrect_ReturnStatus201() throws Exception {
        log.info("Start test: addProduct_DataCorrect_ReturnStatus201");
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("AB");
        productDto.setPrice(9.9);
        productDto.setType("COMPUTER");
        Product product = new Product();
        product.setId(1L);
        product.setName("AB");
        product.setPrice(9.9);
        product.setType(ProductType.COMPUTER);

        when(productService.addProduct(product)).thenReturn(productDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AB"))
                .andExpect(jsonPath("$.price").value(9.9))
                .andExpect(jsonPath("$.type").value("COMPUTER"));

        verify(productService).addProduct(product);
        log.info("Finish test: addProduct_DataCorrect_ReturnStatus201");
    }

    @Test
    void editProduct_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: editProduct_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);
        Product product = new Product();
        product.setId(1L);
        product.setName("AB");
        product.setPrice(9.9);
        product.setType(ProductType.COMPUTER);

        when(productService.editProduct(1L, product)).thenReturn(productDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AB"))
                .andExpect(jsonPath("$.price").value(9.9))
                .andExpect(jsonPath("$.type").value("COMPUTER"))
                .andExpect(jsonPath("$.specificationId").value(10L));

        verify(productService).editProduct(1L, product);
        log.info("Finish test: editProduct_DataCorrect_ReturnStatus200");
    }

    @Test
    void assignProductSpecification_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: assignProductSpecification_DataCorrect_ReturnStatus200");
        ProductDto productDto = new ProductDto(1L, "AB", 9.9, "COMPUTER",
                10L);

        when(productService.addProductSpecification(1L, 2L)).thenReturn(productDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products?productId=1&productSpecId=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AB"))
                .andExpect(jsonPath("$.price").value(9.9))
                .andExpect(jsonPath("$.type").value("COMPUTER"))
                .andExpect(jsonPath("$.specificationId").value(10L));

        verify(productService).addProductSpecification(1L, 2L);
        log.info("Finish test: assignProductSpecification_DataCorrect_ReturnStatus200");
    }

    @Test
    void deleteProduct_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Start test: deleteProduct_DataCorrect_ReturnStatus200");
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
        log.info("Start test: deleteProduct_DataCorrect_ReturnStatus200");
    }
}