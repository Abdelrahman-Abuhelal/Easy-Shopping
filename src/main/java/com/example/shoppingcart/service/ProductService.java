package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product>getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The product with Id -> "+id+" doesn't exist"));
    }

    public Boolean productExistsById(Long id){
        if (id==null){
            throw new IllegalArgumentException("The product id is null");
        }
        if (!productRepository.existsById(id)){
            throw  new ResourceNotFoundException("product with id - > "+ id +" doesn't exist");
        }
    return true;

    }



    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
}
