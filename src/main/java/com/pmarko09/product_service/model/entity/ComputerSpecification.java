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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ComputerSpecification other)) {
            return false;
        }

        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return 0;
        }

        return getClass().hashCode();
    }

    public static void update(ComputerSpecification computerSpecification, ComputerSpecification updateSpec) {
        computerSpecification.setMemorySize(updateSpec.getMemorySize());
        computerSpecification.setProcessor(updateSpec.getProcessor());
    }

    @Override
    public String toString() {
        return "ComputerSpecification{" +
                "processor=" + (processor != null ? processor : "null") +
                ", memorySize=" + (memorySize != null ? memorySize : "null") +
                "} " + super.toString();
    }
}