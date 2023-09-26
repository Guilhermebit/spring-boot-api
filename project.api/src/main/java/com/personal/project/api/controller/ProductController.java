package com.personal.project.api.controller;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/value/{price1}/{price2}")
    public ResponseEntity findProductBetweenPrice(@PathVariable Integer price1, @PathVariable Integer price2) {
            List<Product> listOfProducts = productService.findProductBetweenPrice(price1, price2);
            return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneProduct(@PathVariable String id) {
            Product product = productService.findUniqueProduct(id);
            return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
            List<Product> allProducts = productService.findAllProducts();
            return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity saveProduct(@RequestBody @Valid Product obj) {
            Product product = productService.create(obj);
            //return ResponseEntity.ok().build();
            return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid Product obj) {
            Product product = productService.update(obj);
            return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
    }

}
