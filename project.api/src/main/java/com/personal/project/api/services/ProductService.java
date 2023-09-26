package com.personal.project.api.services;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.repositories.ProductRepository;
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
        Product product = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                  "Product not found! Id: " + id + ", Tipo: " + Product.class.getName()));
        return product;
    }

    public List<Product> findProductBetweenPrice(Integer price1, Integer price2) {
        List<Product> list = productRepository.findByRangeOfPrices(price1, price2);
        if(list == null)
           return null;

        return list;
    }

    public Product findUniqueProduct(String id) {
        Product product = findById(id);
        if(product == null)
           return null;

        return product;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAllByActiveTrue();
    }

    @Transactional
    public Product create(Product obj) {

        if(productRepository.existsById(obj.getId()))
           throw new DataIntegrityViolationException("Id já existe!");

        obj.setActive(true);
        obj = productRepository.save(obj);
        return obj;
    }

    @Transactional
    public Product update(Product obj) {

        Product product = findById(obj.getId());
        if(product == null)
           return null;

        //Product product = optionalProduct.get();
        product.setName(obj.getName());
        product.setPrice_in_cents(obj.getPrice_in_cents());
        product.setActive(obj.getActive());

        return productRepository.save(obj);
    }

    @Transactional
    public void delete(String id) {
        // OBS: I will handle exceptions here
        Product product = findById(id);
        if(product == null)
           return;

        //Product product = optionalProduct.get();
        product.setActive(false);
    }

}
