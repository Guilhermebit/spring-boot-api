package com.personal.project.api.controller;

import com.personal.project.api.domain.product.Product;
import com.personal.project.api.domain.product.ProductRepository;
import com.personal.project.api.dto.RequestProductDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity getsAllProducts() {
        List<Product> allProducts = productRepository.findAllByActiveTrue();

        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProductDTO data) {

          Product newProduct = new Product(data);
          productRepository.save(newProduct);
          return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProductDTO data) {

          Optional<Product> optionalProduct = productRepository.findById(data.id());
          if(optionalProduct.isEmpty())
              ResponseEntity.notFound().build();

          Product product = optionalProduct.get();
          product.setName(data.name());
          product.setPrice_in_cents(data.price_in_cents());

          return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable String id) {

          Optional<Product> optionalProduct = productRepository.findById(id);
          if(optionalProduct.isEmpty())
             ResponseEntity.notFound().build();

          Product product = optionalProduct.get();
          product.setActive(false);

          return ResponseEntity.noContent().build();
    }

}
