package com.example.shoppingcart.entity.order;

import com.example.shoppingcart.entity.ShoppingCart;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private String orderDescription;
    @NotEmpty
    private List<ShoppingCart> cartItems;
    private String customerEmail;
    private String customerUsername;
}
