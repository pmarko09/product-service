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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Product other)) {
            return false;
        }

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", specification=" + specification.getId() +
                '}';
    }

    public static void update(Product product, Product udpatedProduct) {
        product.setName(udpatedProduct.getName());
        product.setPrice(udpatedProduct.getPrice());
        product.setSpecification(udpatedProduct.getSpecification());
    }
}