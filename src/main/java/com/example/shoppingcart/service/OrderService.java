package com.example.shoppingcart.service;

import com.example.shoppingcart.dto.OrderDTO;
import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.entity.Order;
import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.repository.OrderRepository;
import com.example.shoppingcart.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository=productRepository;
    }
    public Order getOrderDetail(Long id){
       Optional<Order>order= orderRepository.findById(id);
       return order.isPresent()? order.get():null;
    }

    public float getCartAmount(List<ShoppingCart>shoppingCartList){

        float totalCartAmount=0f;
        float singleCartAmount=0f;
        long availableQuantity=0;

        for (ShoppingCart cart: shoppingCartList){
           Long productId= cart.getProductId();
           Optional <Product> product=productRepository.findById(productId);
           if (product.isPresent()){
               Product product1=product.get();
               if(product1.getAvailableQuantity()<cart.getQuantity()){
                   singleCartAmount= product1.getAvailableQuantity()*product1.getPrice();
                   cart.setQuantity(product1.getAvailableQuantity());
               }else{
                   singleCartAmount=cart.getQuantity()*product1.getPrice();
                   availableQuantity=product1.getAvailableQuantity()-cart.getQuantity();
               }
               totalCartAmount=totalCartAmount+singleCartAmount;
               product1.setAvailableQuantity(availableQuantity);
               availableQuantity=0;
               cart.setProductName(product1.getName());
               cart.setAmount((long) singleCartAmount);
               productRepository.save(product1);
           }
        }
        return totalCartAmount;
    }

    public Order saveOrder(Order order){
       return orderRepository.save(order);
    }


}
