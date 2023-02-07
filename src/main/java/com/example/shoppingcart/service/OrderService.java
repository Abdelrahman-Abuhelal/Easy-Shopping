package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.order.OrderDTO;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.order.Order;
import com.example.shoppingcart.entity.product.Product;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.exception.OrderException;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.OrderRepository;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository=productRepository;
    }

    public Order getOrderDetail(Long id) throws OrderException {
       Optional<Order>order= orderRepository.findById(id);
       if(!order.isPresent()) {
           String msg = String.format("The order is not available, ID: %d", id);
           throw new OrderException(msg);
       }
       return order.get();
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
    public Order updateOrder(OrderDTO orderDTO,Long id){
        if (orderDTO==null || id ==null){
            throw new ResourceNotFoundException("the order or the id must not be null");
        }
        Optional <Order> order1= orderRepository.findById(id);
        if (!order1.isPresent()){
            throw new ResourceNotFoundException("the order with id "+id+ " does not exist");
        }
        AppUser updatedAppUser=new AppUser(orderDTO.getCustomerUsername(),orderDTO.getCustomerEmail());
        Order updatedOrder =new Order();

        updatedOrder.setId(id);
        updatedOrder.setOrderDescription(orderDTO.getOrderDescription());
        updatedOrder.setAppUser(updatedAppUser);
        updatedOrder.setCartItems(orderDTO.getCartItems());
      return   orderRepository.save(updatedOrder);
    }

    public Order saveOrder(Order order){
       return orderRepository.save(order);
    }


    public Boolean isAcceptedOrder(List<ShoppingCart>shoppingCartList){
        for (ShoppingCart cart: shoppingCartList){
            if (!productRepository.existsById(cart.getId())){
                throw  new ResourceNotFoundException(" product with id - > "+ cart.getId() +" doesn't exist ");
            }
        }
        return true;
    }
}
