package com.example.shoppingcart.controller;

import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
@ContextConfiguration(classes = {ShoppingCartApplication.class})
public class AppUserRegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AppUserService appUserService;

    @Test
    public void getAppUserByEmailExists() throws Exception {
        String existedEmail="malik.hilal14@gmail.com";
        AppUser appUser=new AppUser("Malik",existedEmail);
        Mockito.when(appUserService.getAppUserByEmail(Mockito.anyString())).thenReturn(Optional.of(appUser));
        mockMvc.perform( get("/api/get-AppUser/email/{email}",existedEmail))
                .andExpectAll(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void registerCustomer() throws Exception {
//        CustomerDTO customerDTO=new CustomerDTO("khaled","khaled@gmail.com");
//        Mockito.when(appUserService.registerCustomer(Mockito.any())).thenReturn(customerDTO);
//
//        mockMvc.perform(post("/api-AppUser/add-AppUser").contentType("application/json")
//                .content(objectMapper.writeValueAsString(customerDTO)))
//                .andExpect(status().isOk());
//    }
}
