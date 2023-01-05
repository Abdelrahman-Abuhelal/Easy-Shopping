package com.example.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "myorder")

public class Order {
    @Id @GeneratedValue
    private Long id;
    private String orderDescription;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = ShoppingCart.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<ShoppingCart>cartItems;

    public Order(String orderDescription, Customer customer, List<ShoppingCart> cartItems) {
        this.orderDescription=orderDescription;
        this.customer=customer;
        this.cartItems=cartItems;
    }
}
