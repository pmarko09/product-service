package com.pmarko09.product_service.repository;

import com.pmarko09.product_service.model.entity.Product;
import com.pmarko09.product_service.model.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p")
    List<Product> findAllProducts(Pageable pageable);

    Optional<Product> findByName(String name);

    List<Product> findByType(ProductType type);
}
