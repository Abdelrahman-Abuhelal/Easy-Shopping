package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public List<Customer>getAllCustomer(){
        return customerRepository.findAll();
    }

    public Long isCustomerPresent(Customer customer){
        Customer customer1= customerRepository.getCustomerByEmailAndName(customer.getEmail(), customer.getName());
        return customer1!=null ? customer1.getId():null;
    }

    public Optional<Customer> getCustomerByEmail(String email){
        return customerRepository.getCustomersByEmail(email);
    }


}
