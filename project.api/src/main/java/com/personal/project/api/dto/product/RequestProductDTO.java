package com.personal.project.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductDTO {
        @NotBlank(message = "Product name should not be empty or null.")
        private String name;
        @NotNull(message = "Price should not be empty or null.")
        private Integer price_in_cents;
}
