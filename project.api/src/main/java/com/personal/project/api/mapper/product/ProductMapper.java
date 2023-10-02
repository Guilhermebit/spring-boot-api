package com.personal.project.api.mapper.product;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.dto.product.ResponseProductDTO;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product mapToProduct(RequestProductDTO requestProductDTO) {
        return new Product(
                requestProductDTO.getName(),
                requestProductDTO.getPrice_in_cents()
        );
    }

    public static RequestProductDTO mapToProductDTO(Product product) {
        return new RequestProductDTO(
                product.getName(),
                product.getPrice_in_cents()
        );
    }

    public static List<RequestProductDTO> toRequestProductDTOList(List<Product> entities) {
        return entities.stream()
                .map(ProductMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    public static ResponseProductDTO mapToResponseProductDTO(Product product) {
        return new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice_in_cents()
        );
    }

    public static List<ResponseProductDTO> toResponseProductDTOList(List<Product> entities) {
        return entities.stream()
                .map(ProductMapper::mapToResponseProductDTO)
                .collect(Collectors.toList());
    }
}