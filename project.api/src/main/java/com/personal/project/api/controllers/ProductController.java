package com.personal.project.api.controllers;

import com.personal.project.api.models.product.Product;
import com.personal.project.api.responses.ResponseHandler;
import com.personal.project.api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            if(listOfProducts.isEmpty())
                return ResponseHandler.responseBuilder(HttpStatus.OK, "No products were found within the declared range.", listOfProducts);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneProduct(@PathVariable String id) {
            Product product = productService.findUniqueProduct(id);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Product not found.", product);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", product);
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
            List<Product> allProducts = productService.findAllProducts();
            if(allProducts.isEmpty())
                return ResponseHandler.responseBuilder(HttpStatus.OK, "No products were found.", allProducts);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", allProducts);
    }

    @PostMapping
    public ResponseEntity saveProduct(@RequestBody @Valid Product obj) {
            Product product = productService.create(obj);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to register product.", product);
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "", product);
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid Product obj) {
            Product product = productService.update(obj);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to update product.", product);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
            Product product = productService.delete(id);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to delete product.", product);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", product);
    }

}
