package com.example.shoppingcart.controller;

import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product>products=  productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @PostMapping("/add-product")
    public ResponseEntity<Product>addProduct(@RequestBody Product product){
        Product product1= productService.saveProduct(product);
        return ResponseEntity.ok(product1);
    }
}
