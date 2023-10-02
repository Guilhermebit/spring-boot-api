package com.personal.project.api.services;

import com.personal.project.api.mapper.product.ProductMapper;
import com.personal.project.api.models.product.Product;
import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.models.user.User;
import com.personal.project.api.repositories.ProductRepository;
import com.personal.project.api.dto.product.ResponseProductDTO;
import com.personal.project.api.services.exceptions.AuthorizationException;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Service
public class ProductService implements ProductInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private Boolean userHasProducts(User user, Product product) {
        return product.getActive() && product.getUser().getId().equals(user.getId());
    }

    private Product findProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found! Id: " + id + ", Type: " + Product.class.getName()));

        User userAuth = authenticationService.findAuthenticatedUser();
        if(!userHasProducts(userAuth, product))
           throw new AuthorizationException("Product not found.");

        return product;
    }

    @Override
    public List<ResponseProductDTO> findProductBetweenPrice(Integer price1, Integer price2) {
        User userAuth = authenticationService.findAuthenticatedUser();

        List<Product> products = productRepository.findByRangeOfPrices(price1, price2, userAuth.getId());
        return ProductMapper.toResponseProductDTOList(products);
    }

    @Override
    public ResponseProductDTO findUniqueProduct(String productId) {
        Product product = findProductById(productId);
        if(Objects.isNull(product))
            return null;

        return ProductMapper.mapToResponseProductDTO(product);
    }

    @Override
    public List<ResponseProductDTO> findAllProducts() {
        User userAuth = authenticationService.findAuthenticatedUser();

        List<Product> products = productRepository.findAllByUserId(userAuth.getId());
        return ProductMapper.toResponseProductDTOList(products);
    }

    @Override
    @Transactional
    public ResponseProductDTO create(RequestProductDTO requestProductDTO) {
        Product product = ProductMapper.mapToProduct(requestProductDTO);
        product.setActive(true);
        product.setUser(authenticationService.findAuthenticatedUser());
        Product savedProduct = productRepository.save(product);

        return ProductMapper.mapToResponseProductDTO(savedProduct);
    }

    @Override
    @Transactional
    public ResponseProductDTO update(RequestProductDTO obj, String id) {
        Product product = findProductById(id);
        product.setName(obj.getName());
        product.setPrice_in_cents(obj.getPrice_in_cents());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.mapToResponseProductDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = findProductById(id);
        product.setActive(false);
    }

}
