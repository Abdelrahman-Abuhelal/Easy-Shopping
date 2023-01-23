package com.example.shoppingcart.controller;

import com.example.shoppingcart.dto.OrderDTO;
import com.example.shoppingcart.dto.ResponseOrderDTO;
import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.entity.Order;
import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.service.CustomerService;
import com.example.shoppingcart.service.OrderService;
import com.example.shoppingcart.service.ProductService;
import com.example.shoppingcart.util.DateUtil;
import org.aspectj.weaver.ast.Or;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api-order")
public class ShoppingCartController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    @Autowired
    public ShoppingCartController(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }


    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<Order>updateOrder(@RequestBody OrderDTO orderDTO,@PathVariable Long orderId){
       Order order= orderService.updateOrder(orderDTO,orderId);
       return ResponseEntity.ok(order);
    }



    @PostMapping("/place-order")
   public ResponseEntity<Object>placeOrder(@RequestBody OrderDTO orderDTO) {
        ResponseOrderDTO responseOrderDTO=new ResponseOrderDTO();
        for (ShoppingCart cart: orderDTO.getCartItems()){
                productService.productExistsById(cart.getProductId());
            }
        float amount = orderService.getCartAmount(orderDTO.getCartItems());
        Customer customer=new Customer(orderDTO.getCustomerName(),orderDTO.getCustomerEmail());
        Long CustomerIdFromDb=customerService.isCustomerPresent(customer);
        if (CustomerIdFromDb!=null){
            customer.setId(CustomerIdFromDb);
        }else{
           customer= customerService.saveCustomer(customer);
        }
        Order order=new Order(orderDTO.getOrderDescription(),customer,orderDTO.getCartItems());
        order=orderService.saveOrder(order);
        responseOrderDTO.setOrderId(customer.getId());
        responseOrderDTO.setOrderDescription(order.getOrderDescription());
        responseOrderDTO.setInvoiceNumber(new Random().nextLong(1000));
        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(DateUtil.getCurrentDateTime());

        return ResponseEntity.ok(responseOrderDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order>getOrderDetail(@PathVariable Long orderId){
       Order order= orderService.getOrderDetail(orderId);
        return  ResponseEntity.ok(order);
    }

}
