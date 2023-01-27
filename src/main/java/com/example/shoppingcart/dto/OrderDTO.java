package com.example.shoppingcart.dto;

import com.example.shoppingcart.entity.ShoppingCart;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private String orderDescription;
    private List<ShoppingCart> cartItems;
    private String customerEmail;
    private String customerUsername;
}
