package com.pmarko09.product_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RAMS")
public class Ram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    public static void update(Ram ram, Ram updatedRam) {
        ram.setSize(updatedRam.getSize());
    }
}
