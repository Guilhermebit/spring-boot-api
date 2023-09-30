package com.personal.project.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
        private String id;
        @NotBlank(message = "Name should not be empty or null.")
        private String name;
        @NotNull(message = "Price should not be empty or null.")
        private Integer price_in_cents;
        private Boolean active;
}
