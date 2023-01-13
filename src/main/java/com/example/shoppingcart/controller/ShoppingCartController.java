package com.example.shoppingcart.controller;

import com.example.shoppingcart.dto.OrderDTO;
import com.example.shoppingcart.dto.ResponseOrderDTO;
import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.entity.Order;
import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.service.CustomerService;
import com.example.shoppingcart.service.OrderService;
import com.example.shoppingcart.service.ProductService;
import com.example.shoppingcart.util.DateUtil;
import org.aspectj.weaver.ast.Or;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    public ShoppingCartController(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }
    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>>getAllProducts(){
      List<Product>products=  productService.getAllProducts();
      return ResponseEntity.ok(products);
    }

    @GetMapping("/get-all-customers")
    public ResponseEntity<List<Customer>>getAllCustomers(){
        List<Customer>customers=  customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product>addProduct(@RequestBody Product product){
       Product product1= productService.saveProduct(product);
       return ResponseEntity.ok(product1);
    }

    @PostMapping("/add-customer")
    public ResponseEntity<Customer>addCustomer(@RequestBody Customer customer){
        Customer customer1= customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer1);
    }



    @PostMapping("/place-order")
   public ResponseEntity<ResponseOrderDTO>placeOrder(@RequestBody OrderDTO orderDTO){
        ResponseOrderDTO responseOrderDTO=new ResponseOrderDTO();
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
        responseOrderDTO.setInvoiceNumber(new Random(1000).nextLong());
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
