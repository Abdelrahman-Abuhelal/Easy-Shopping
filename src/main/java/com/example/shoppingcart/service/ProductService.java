package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.product.Product;
import com.example.shoppingcart.entity.product.ProductDTO;
import com.example.shoppingcart.entity.product.ProductDTOMapper;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    private ProductDTOMapper productDTOMapper;

    private Logger log= LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO>getAllProducts(){
        return productRepository.findAll().stream().map(productDTOMapper).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id){
        Product product= productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The product with Id -> "+id+" doesn't exist"));
        return productDTOMapper.apply(product);
    }

    public Boolean productExistsById(Long id){
        if (id==null){
            log.error("The product id is null");
            throw new IllegalArgumentException("The product id is null");
        }
        if (!productRepository.existsById(id)){
            log.error("product with id - > {} doesn't exist",id);
            throw  new ResourceNotFoundException("product with id - > "+ id +" doesn't exist");
        }
    return true;

    }



    public ProductDTO saveProduct(Product product){
        Product product1= productRepository.save(product);
        return productDTOMapper.apply(product1);
    }
}
