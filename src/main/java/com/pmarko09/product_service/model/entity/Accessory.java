package com.pmarko09.product_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCESSORIES")
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public static void update(Accessory accessory, Accessory updatedAccessory) {
        accessory.setName(updatedAccessory.getName());
    }
}