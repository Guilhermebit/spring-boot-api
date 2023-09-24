package com.personal.project.api.services;

import com.personal.project.api.domain.product.Product;
import com.personal.project.api.domain.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Optional<Product> findById(String id) {
        // OBS: I will handle exceptions here
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct;
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAllByActiveTrue();
        return allProducts;
    }

    @Transactional
    public Product create(Product obj) {
        obj.setActive(true);
        obj = productRepository.save(obj);
        return obj;
    }

    @Transactional
    public Product update(Product obj) {

        Optional<Product> optionalProduct = findById(obj.getId());
        if(optionalProduct.isEmpty())
           return null;

        Product product = optionalProduct.get();
        product.setName(obj.getName());
        product.setPrice_in_cents(obj.getPrice_in_cents());
        product.setActive(obj.getActive());

        return productRepository.save(obj);
    }

    @Transactional
    public void delete(String id) {
        // OBS: I will handle exceptions here
        Optional<Product> optionalProduct = findById(id);
        if(optionalProduct.isEmpty())
           return;

        Product product = optionalProduct.get();
        product.setActive(false);
    }

}
