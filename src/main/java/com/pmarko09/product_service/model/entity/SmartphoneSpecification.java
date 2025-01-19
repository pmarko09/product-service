package com.pmarko09.product_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SMARTPHONE_SPECIFICATION")
public class SmartphoneSpecification extends ProductSpecification {
    private String color;
    private Integer batteryCapacity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "smartphone_accessories",
            joinColumns = @JoinColumn(name = "specification_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private Set<Accessory> accessories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SmartphoneSpecification other)) {
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

    @Override
    public String toString() {
        return "SmartphoneSpecification{" +
                "color='" + color + '\'' +
                ", batteryCapacity=" + batteryCapacity +
                ", accessories=" + accessories.stream().map(Accessory::getId).toList() +
                "} " + super.toString();
    }

    public static void update(SmartphoneSpecification specification, SmartphoneSpecification updatedSpecification) {
        specification.setColor(updatedSpecification.getColor());
        specification.setBatteryCapacity(updatedSpecification.getBatteryCapacity());
        specification.setAccessories(updatedSpecification.getAccessories());
    }
}