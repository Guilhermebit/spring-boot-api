package com.personal.project.api.models.product;

import com.personal.project.api.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Table(name = Product.TABLE_NAME, schema = Product.TABLE_SCHEMA)
@Entity(name = "product")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    public static final String TABLE_NAME = "product";
    public static final String TABLE_SCHEMA = "sch_application";

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    @NotBlank()
    private String name;

    @Column(name = "price_in_cents")
    @NotNull()
    private Integer price_in_cents;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public Product(String name, Integer price_in_cents) {
         this.name = name;
         this.price_in_cents = price_in_cents;
    }

}