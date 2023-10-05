package com.personal.project.api.services;

import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.dto.product.ResponseProductDTO;
import java.util.List;

public interface ProductInterface {
    List<ResponseProductDTO> findProductBetweenPrice(Integer price1, Integer price2);
    ResponseProductDTO findUniqueProduct(String id);
    List<ResponseProductDTO> findAllProducts();
    ResponseProductDTO create(RequestProductDTO requestProductDTO);
    ResponseProductDTO update(String id, RequestProductDTO requestProductDTO);
    void delete(String id);
}
