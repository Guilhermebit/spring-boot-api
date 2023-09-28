package com.personal.project.api.controllers;

import com.personal.project.api.dto.product.ProductDTO;
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
    public ResponseEntity<Object> findProductBetweenPrice(@PathVariable Integer price1, @PathVariable Integer price2) {
            List<ProductDTO> listOfProducts = productService.findProductBetweenPrice(price1, price2);
            if(listOfProducts.isEmpty())
                return ResponseHandler.responseBuilder(HttpStatus.OK, "No products were found within the declared range.", listOfProducts);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable String id) {
            ProductDTO product = productService.findUniqueProduct(id);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Product not found.", null);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", product);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
            List<ProductDTO> allProducts = productService.findAllProducts();
            if(allProducts.isEmpty())
                return ResponseHandler.responseBuilder(HttpStatus.OK, "No products were found.", allProducts);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", allProducts);
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
            ProductDTO productCreated = productService.create(productDTO);
            if(productCreated == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to register product.", null);
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "", productCreated);
    }

    @PutMapping
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
            ProductDTO productUpdated = productService.update(productDTO);
            if(productUpdated == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to update product.", null);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", productUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
            productService.delete(id);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "Product successfully deleted.", null);
    }

}
