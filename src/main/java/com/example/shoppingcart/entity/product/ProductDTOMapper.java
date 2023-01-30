package com.example.shoppingcart.entity.product;

import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.customer.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(product.getName(), product.getPrice());
    }
}
