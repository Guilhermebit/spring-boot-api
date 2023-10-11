package com.personal.project.api.services;

import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.mapper.ProductMapper;
import com.personal.project.api.models.product.Product;
import com.personal.project.api.models.user.User;
import com.personal.project.api.repositories.ProductRepository;
import com.personal.project.api.dto.product.ResponseProductDTO;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private  ProductMapper productMapper;


    private Product findProductById(@NotBlank String productId) {
          User userAuth = userService.findAuthenticatedUser();
          Optional<Product> product = productRepository.findProductByUserId(productId, userAuth.getId());
          return product.orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found for user! Id: " + productId + ", Type: " + Product.class.getName()));
    }

    public List<ResponseProductDTO> findProductBetweenPrice(@Positive @NotNull Integer price1, @Positive @NotNull Integer price2) {
        User userAuth = userService.findAuthenticatedUser();
        List<Product> products = productRepository.findByRangeOfPrices(price1, price2, userAuth.getId());
        return productMapper.toResponseProductDTOList(products);
    }

    public ResponseProductDTO findUniqueProduct(@NotBlank String productId) {
        return productMapper.mapToResponseProductDTO(findProductById(productId));
    }

    public List<ResponseProductDTO> findAllProducts() {
        User userAuth = userService.findAuthenticatedUser();
        List<Product> products = productRepository.findAllByUserId(userAuth.getId());
        return productMapper.toResponseProductDTOList(products);
    }

    @Transactional
    public ResponseProductDTO create(@Valid RequestProductDTO requestProductDTO) {
        Product product = productMapper.mapToProduct(requestProductDTO);
        product.setActive(true);
        product.setUser(userService.findAuthenticatedUser());
        return productMapper.mapToResponseProductDTO(productRepository.save(product));
    }

    @Transactional
    public ResponseProductDTO update(@NotBlank String id, @Valid RequestProductDTO requestProductDTO) {
        Product product = findProductById(id);
        product.setName(requestProductDTO.name());
        product.setPrice_in_cents(requestProductDTO.price_in_cents());
        return productMapper.mapToResponseProductDTO(productRepository.save(product));
    }

    @Transactional
    public void delete(@NotBlank String id) {
        findProductById(id).setActive(false);
    }

}
