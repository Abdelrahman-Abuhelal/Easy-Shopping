package com.example.shoppingcart.controller;

import com.example.shoppingcart.entity.product.Product;
import com.example.shoppingcart.entity.product.ProductDTO;
import com.example.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO>products=  productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @PostMapping("/add-product")
    public ResponseEntity<ProductDTO>addProduct(@RequestBody Product product){
        ProductDTO product1= productService.saveProduct(product);
        return ResponseEntity.ok(product1);
    }



}
