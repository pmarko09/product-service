package com.pmarko09.product_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMPUTER_SPECIFICATION")
public class ComputerSpecification extends ProductSpecification {

    @ManyToOne
    @JoinColumn(name = "processor_id", referencedColumnName = "id")
    private Processor processor;

    @ManyToOne
    @JoinColumn(name = "ram_id", referencedColumnName = "id")
    private Ram memorySize;

    public static void update(ComputerSpecification computerSpecification, ComputerSpecification updateSpec) {
        computerSpecification.setMemorySize(updateSpec.getMemorySize());
        computerSpecification.setProcessor(updateSpec.getProcessor());
    }
}