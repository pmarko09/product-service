package com.pmarko09.product_service.mapper;

import com.pmarko09.product_service.model.dto.ProductDto;
import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProductMapperTest {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void mapProductToDto() {
        log.info("Start test: mapProductToDto");
        Product product = new Product();
        product.setId(1L);
        product.setPrice(9.9);
        product.setName("AB");
        product.setType(ProductType.COMPUTER);

        ProductDto result = productMapper.toDto(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(9.9, result.getPrice());
        assertEquals("AB", result.getName());
        assertEquals(ProductType.COMPUTER.toString(), result.getType());
        log.info("Finish test: mapProductToDto");
    }
}
