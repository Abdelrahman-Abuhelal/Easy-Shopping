package com.example.shoppingcart.controller;

import com.example.shoppingcart.entity.order.OrderDTO;
import com.example.shoppingcart.entity.order.ResponseOrderDTO;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.order.Order;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.exception.OrderException;
import com.example.shoppingcart.service.AppUserService;
import com.example.shoppingcart.service.OrderService;
import com.example.shoppingcart.service.ProductService;
import com.example.shoppingcart.util.DateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api-order")
public class ShoppingCartController {
    private final OrderService orderService;
    private final AppUserService appUserService;
    private final ProductService productService;
    @Autowired
    public ShoppingCartController(OrderService orderService, AppUserService appUserService, ProductService productService) {
        this.orderService = orderService;
        this.appUserService = appUserService;
        this.productService = productService;
    }


    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<Order>updateOrder(@RequestBody OrderDTO orderDTO,@PathVariable Long orderId){
       Order order= orderService.updateOrder(orderDTO,orderId);
       return ResponseEntity.ok(order);
    }



    @PostMapping("/place-order")
   public ResponseEntity<?>placeOrder(@RequestBody @Valid OrderDTO orderDTO) {
        ResponseOrderDTO responseOrderDTO=new ResponseOrderDTO();
        for (ShoppingCart cart: orderDTO.getCartItems()){
                productService.productExistsById(cart.getProductId());
            }
        float amount = orderService.getCartAmount(orderDTO.getCartItems());
        AppUser appUser=new AppUser(orderDTO.getCustomerUsername(),orderDTO.getCustomerEmail());
        Long appUserIdFromDb= appUserService.isAppUserPresent(appUser);
        appUser.setId(appUserIdFromDb);
        Order order=new Order(orderDTO.getOrderDescription(),appUser,orderDTO.getCartItems());
        order=orderService.saveOrder(order);
        responseOrderDTO.setOrderId(appUser.getId());
        responseOrderDTO.setOrderDescription(order.getOrderDescription());
        responseOrderDTO.setInvoiceNumber(new Random(1000).nextLong());
        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(DateUtil.getCurrentDateTime());

        return ResponseEntity.ok(responseOrderDTO);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity getOrderDetail(@PathVariable Long orderId){
        Order order= null;
        try {
            order = orderService.getOrderDetail(orderId);
            return  ResponseEntity.ok(order);
        } catch (OrderException e) {
            // logger
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
