package com.example.productservice.productsubdomain.dataaccesslayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ProductIdentifier productIdentifier;

    private String productName;
    private String category;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    private Double price;
    private Integer stock;
    private String productDescription;

    public Product(@NotNull Integer id,@NotNull String productName,@NotNull String category,@NotNull Brand brand,
                   @NotNull Double price,@NotNull String productDescription) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.productDescription = productDescription;
    }
}
