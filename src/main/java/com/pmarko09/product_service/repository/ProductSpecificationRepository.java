package com.pmarko09.product_service.repository;

import com.pmarko09.product_service.model.entity.ComputerSpecification;
import com.pmarko09.product_service.model.entity.ProductSpecification;
import com.pmarko09.product_service.model.entity.SmartphoneSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> {

    @Query("SELECT c FROM ComputerSpecification c")
    List<ComputerSpecification> findAllComputerSpecifications();

    @Query("SELECT s FROM SmartphoneSpecification s")
    List<SmartphoneSpecification> findAllSmartphoneSpecifications();
}