package com.example.shoppingcart;

import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.entity.Order;
import com.example.shoppingcart.service.CustomerService;
import com.example.shoppingcart.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShoppingCartApplication.class})
public class ProjectTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void findCustomerByExistedEmail(){
        String existedEmail="malik.hilal14@gmail.com";
      Optional<Customer> customer= customerService.getCustomerByEmail(existedEmail);
      assertEquals(true,customer.isPresent());
      assertEquals(existedEmail,customer.get().getEmail());
    }

    @Test
    public void findCustomerByNonExistedEmail(){
        String nonExistedEmail="NotExistedCustomer@gmail.com";
        Optional<Customer> customer= customerService.getCustomerByEmail(nonExistedEmail);
        assertEquals(false,customer.isPresent());
    }

}
