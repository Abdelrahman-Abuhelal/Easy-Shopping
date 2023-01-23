package com.example.shoppingcart.controller;

import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.repository.CustomerRepository;
import com.example.shoppingcart.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShoppingCartApplication.class})
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;

    @Test
    public void getCustomerByEmailExists() throws Exception {
        String existedEmail="malik.hilal14@gmail.com";
        Customer customer=new Customer("Malik",existedEmail);
        Mockito.when(customerService.getCustomerByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
        mockMvc.perform( get("/api/get-customer/email/{email}",existedEmail))
                .andExpectAll(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void addCustomer() throws Exception {
        Customer customer=new Customer("khaled","khaled@gmail.com");
        Mockito.when(customerService.saveCustomer(Mockito.any())).thenReturn(customer);

        mockMvc.perform(post("/api-customer/add-customer").contentType("application/json")
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }
}
