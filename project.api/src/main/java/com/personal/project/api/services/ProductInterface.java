package com.personal.project.api.services;

import com.personal.project.api.dto.product.ProductDTO;
import java.util.List;

public interface ProductInterface {

    List<ProductDTO> findProductBetweenPrice(Integer price1, Integer price2);
    ProductDTO findUniqueProduct(String id);
    List<ProductDTO> findAllProducts();
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(ProductDTO obj);
    void delete(String id);

}
