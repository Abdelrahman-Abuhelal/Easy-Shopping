package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findCustomerByNonExistedEmail(){
        String existedEmail="malik.hilal14@gmail.com";
        Optional<Customer> customer= customerRepository.getCustomersByEmail(existedEmail);
        assertEquals(true,customer.isPresent());
        assertEquals(existedEmail,customer.get().getEmail());
    }
}
