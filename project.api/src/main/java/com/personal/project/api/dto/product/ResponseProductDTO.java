package com.personal.project.api.dto.product;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDTO {
    private String id;
    private String name;
    private Integer price_in_cents;
}



