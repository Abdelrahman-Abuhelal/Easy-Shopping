package com.example.shoppingcart.service;

import com.example.shoppingcart.repository.CustomerRepository;
import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShoppingCartApplication.class})
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void findCustomerByEmailExists(){
        String existedEmail="malik.hilal14@gmail.com";
        Customer customer=new Customer("Malik",existedEmail);
        Mockito.when(customerRepository.getCustomersByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
       Optional<Customer>customer1= customerService.getCustomerByEmail(existedEmail);
       assertEquals(true,customer1.isPresent());
    }
}
