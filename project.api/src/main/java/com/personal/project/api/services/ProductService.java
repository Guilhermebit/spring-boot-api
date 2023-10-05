package com.personal.project.api.services;

import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.mapper.ProductMapper;
import com.personal.project.api.models.product.Product;
import com.personal.project.api.models.user.User;
import com.personal.project.api.repositories.ProductRepository;
import com.personal.project.api.dto.product.ResponseProductDTO;
import com.personal.project.api.services.exceptions.AuthorizationException;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductService implements ProductInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    private Boolean userHasProducts(User user, Product product) {
        return product.getActive() && product.getUser().getId().equals(user.getId());
    }

    private Product findProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found! Id: " + id + ", Type: " + Product.class.getName()));

        User userAuth = userService.findAuthenticatedUser();
        if(!userHasProducts(userAuth, product))
           throw new AuthorizationException("Product not found.");

        return product;
    }

    @Override
    public List<ResponseProductDTO> findProductBetweenPrice(Integer price1, Integer price2) {
        User userAuth = userService.findAuthenticatedUser();
        List<Product> products = productRepository.findByRangeOfPrices(price1, price2, userAuth.getId());
        return ProductMapper.toResponseProductDTOList(products);
    }

    @Override
    public ResponseProductDTO findUniqueProduct(String productId) {
        return ProductMapper.mapToResponseProductDTO(findProductById(productId));
    }

    @Override
    public List<ResponseProductDTO> findAllProducts() {
        User userAuth = userService.findAuthenticatedUser();
        List<Product> products = productRepository.findAllByUserId(userAuth.getId());
        return ProductMapper.toResponseProductDTOList(products);
    }

    @Override
    @Transactional
    public ResponseProductDTO create(RequestProductDTO requestProductDTO) {
        Product product = ProductMapper.mapToProduct(requestProductDTO);
        product.setActive(true);
        product.setUser(userService.findAuthenticatedUser());
        return ProductMapper.mapToResponseProductDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public ResponseProductDTO update(String id, RequestProductDTO requestProductDTO) {
        Product product = findProductById(id);
        product.setName(requestProductDTO.name());
        product.setPrice_in_cents(requestProductDTO.price_in_cents());
        return ProductMapper.mapToResponseProductDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = findProductById(id);
        product.setActive(false);
    }

}
