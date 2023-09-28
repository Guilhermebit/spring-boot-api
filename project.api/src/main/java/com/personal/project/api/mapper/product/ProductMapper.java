package com.personal.project.api.mapper.product;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.dto.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice_in_cents(),
                product.getActive()
        );
    }

    public static Product mapToProduct(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getPrice_in_cents(),
                productDTO.getActive()
        );
    }

    public static List<ProductDTO> toDTOList(List<Product> entities) {
        return entities.stream()
                .map(ProductMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }
}