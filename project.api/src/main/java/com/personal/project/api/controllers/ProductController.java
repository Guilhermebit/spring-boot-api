package com.personal.project.api.controllers;

import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.responses.ResponseHandler;
import com.personal.project.api.dto.product.ResponseProductDTO;
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
    public ResponseEntity<Object> getProductBetweenPrice(@PathVariable Integer price1, @PathVariable Integer price2) {
            List<ResponseProductDTO> listOfProducts = productService.findProductBetweenPrice(price1, price2);
            if(listOfProducts.isEmpty())
                return ResponseHandler.responseBuilder(HttpStatus.OK, "No products were found within the declared range.", listOfProducts);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable String id) {
            ResponseProductDTO product = productService.findUniqueProduct(id);
            if(product == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Product not found.", null);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", product);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
            List<ResponseProductDTO> allProducts = productService.findAllProducts();
            if(allProducts.isEmpty())
               return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, "No products were found.", allProducts);
            return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid RequestProductDTO requestProductDTO) {
            ResponseProductDTO productCreated = productService.create(requestProductDTO);
            if(productCreated == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to register product.", null);
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "", productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid RequestProductDTO requestProductDTO, @PathVariable String id) {
            ResponseProductDTO productUpdated = productService.update(requestProductDTO, id);
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
