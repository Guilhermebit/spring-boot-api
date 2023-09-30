package com.personal.project.api.models.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Table(name = Product.TABLE_NAME, schema = "sch_application")
@Entity(name = "product")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    public static final String TABLE_NAME = "product";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price_in_cents")
    private Integer price_in_cents;

    @Column(name = "active")
    private Boolean active;

}