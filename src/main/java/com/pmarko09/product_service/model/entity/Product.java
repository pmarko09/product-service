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
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "specification_id", referencedColumnName = "id")
    private ProductSpecification specification;

    public static void update(Product product, Product udpatedProduct) {
        product.setName(udpatedProduct.getName());
        product.setPrice(udpatedProduct.getPrice());
        product.setSpecification(udpatedProduct.getSpecification());
    }
}