package com.personal.project.api.domain.product;

import jakarta.persistence.*;
import lombok.*;


@Table(name="product", schema="sch_application")
@Entity(name="product")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer price_in_cents;

    private Boolean active = true;

}