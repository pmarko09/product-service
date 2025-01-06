package com.pmarko09.product_service.repository;

import com.pmarko09.product_service.model.entity.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {

    Optional<Ram> findBySize(String size);

    @Query("SELECT r FROM Ram r")
    List<Ram> findAllRams(Pageable pageable);
}
