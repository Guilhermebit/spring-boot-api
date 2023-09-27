package com.personal.project.api.services;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.repositories.ProductRepository;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    private Product findById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found! Id: " + id + ", Tipo: " + Product.class.getName()));
    }

    public List<Product> findProductBetweenPrice(Integer price1, Integer price2) {
        return productRepository.findByRangeOfPrices(price1, price2);
    }

    public Product findUniqueProduct(String id) {
        return findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAllByActiveTrue();
    }

    @Transactional
    public Product create(Product obj) {

        if(obj.getId() != null && productRepository.existsById(obj.getId()))
           throw new DataIntegrityViolationException("Id já existe!");

        obj.setActive(true);
        obj = productRepository.save(obj);
        return obj;
    }

    @Transactional
    public Product update(Product obj) {

        Product product = findById(obj.getId());

        //Product product = optionalProduct.get();
        product.setName(obj.getName());
        product.setPrice_in_cents(obj.getPrice_in_cents());
        product.setActive(obj.getActive());

        return productRepository.save(obj);
    }

    @Transactional
    public Product delete(String id) {
        Product product = findById(id);
        //Product product = optionalProduct.get();
        product.setActive(false);
        return product;
    }

}
