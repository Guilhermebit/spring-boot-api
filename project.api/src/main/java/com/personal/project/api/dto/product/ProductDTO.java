package com.personal.project.api.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
        private String id;
        private String name;
        private Integer price_in_cents;
        private Boolean active;
}
