package com.personal.project.api.domain.product;

import com.personal.project.api.dto.RequestProductDTO;
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

    private Boolean active;

    public Product(RequestProductDTO requestProduct){
      this.name = requestProduct.name();
      this.price_in_cents = requestProduct.price_in_cents();
      this.active = true;
    }

}