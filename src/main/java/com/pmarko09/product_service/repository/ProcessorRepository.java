package com.pmarko09.product_service.repository;

import com.pmarko09.product_service.model.entity.Processor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Long> {

    Optional<Processor> findByName(String name);

    @Query("SELECT p FROM Processor p")
    List<Processor> findAllProcessors(Pageable pageable);
}