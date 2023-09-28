package com.personal.project.api.services;

import com.personal.project.api.mapper.product.ProductMapper;
import com.personal.project.api.models.product.Product;
import com.personal.project.api.dto.product.ProductDTO;
import com.personal.project.api.repositories.ProductRepository;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    private Product findById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found! Id: " + id + ", Type: " + Product.class.getName()));
    }

    public List<ProductDTO> findProductBetweenPrice(Integer price1, Integer price2) {
        List<Product> products = productRepository.findByRangeOfPrices(price1, price2);
        return ProductMapper.toDTOList(products);
    }

    public ProductDTO findUniqueProduct(String id) {
        return ProductMapper.mapToProductDTO(findById(id));
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAllByActiveTrue();
        return ProductMapper.toDTOList(products);
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {

        Product product = ProductMapper.mapToProduct(productDTO);

        if(product.getId()!= null && productRepository.existsById(product.getId()))
           throw new DataIntegrityViolationException("The Id already exists!");

        product.setActive(true);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.mapToProductDTO(savedProduct);

    }

    @Transactional
    public ProductDTO update(ProductDTO obj) {

        Product product = findById(obj.getId());

        product.setName(obj.getName());
        product.setPrice_in_cents(obj.getPrice_in_cents());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.mapToProductDTO(updatedProduct);
    }

    @Transactional
    public void delete(String id) {
        Product product = findById(id);
        product.setActive(false);
    }

}
