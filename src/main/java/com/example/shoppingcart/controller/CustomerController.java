package com.example.shoppingcart.controller;

import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api-customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/get-all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer>customers=  customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/get-customer/email/{email}")
    public ResponseEntity<Customer>getCustomerByEmail(@PathVariable String email){
        Optional<Customer> customer=  customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer.get());
    }

    @PostMapping("/add-customer")
    public ResponseEntity<Customer>addCustomer(@RequestBody Customer customer){
        Customer customer1= customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer1);
    }



}
