package com.pmarko09.product_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROCESSORS")
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public static void update(Processor processor, Processor updatedProcessor) {
        processor.setName(updatedProcessor.getName());
    }
}