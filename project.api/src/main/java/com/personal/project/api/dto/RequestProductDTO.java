package com.personal.project.api.dto;

import com.personal.project.api.domain.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProductDTO(

        String id,

        @NotNull
        String name,

        Integer price_in_cents,

        Boolean active) {
}
