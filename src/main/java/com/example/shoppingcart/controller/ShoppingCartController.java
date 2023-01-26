package com.example.shoppingcart.controller;

import com.example.shoppingcart.dto.OrderDTO;
import com.example.shoppingcart.dto.ResponseOrderDTO;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.Order;
import com.example.shoppingcart.entity.ShoppingCart;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.service.AppUserService;
import com.example.shoppingcart.service.OrderService;
import com.example.shoppingcart.service.ProductService;
import com.example.shoppingcart.util.DateUtil;
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
   public ResponseEntity<?>placeOrder(@RequestBody OrderDTO orderDTO) {
        ResponseOrderDTO responseOrderDTO=new ResponseOrderDTO();
        for (ShoppingCart cart: orderDTO.getCartItems()){
                productService.productExistsById(cart.getProductId());
            }
        float amount = orderService.getCartAmount(orderDTO.getCartItems());
        AppUser appUser=new AppUser(orderDTO.getCustomerUsername(),orderDTO.getCustomerEmail());
        Long appUserIdFromDb= appUserService.isAppUserPresent(appUser);
        if (appUserIdFromDb!=null){
            appUser.setId(appUserIdFromDb);
        }else{
            throw new ResourceNotFoundException("Please register before Ordering");
        }
        Order order=new Order(orderDTO.getOrderDescription(),appUser,orderDTO.getCartItems());
        order=orderService.saveOrder(order);
        responseOrderDTO.setOrderId(appUser.getId());
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
