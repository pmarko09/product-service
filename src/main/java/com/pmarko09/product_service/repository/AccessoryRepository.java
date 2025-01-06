package com.pmarko09.product_service.repository;

import com.pmarko09.product_service.model.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    @Query("SELECT a FROM Accessory a")
    List<Accessory> findAllAccessories(Pageable pageable);

    Optional<Accessory> findByName(String name);
}