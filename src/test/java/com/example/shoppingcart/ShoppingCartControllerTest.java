package com.example.shoppingcart;

import com.example.shoppingcart.controller.ShoppingCartController;
import com.example.shoppingcart.entity.Product;
import com.example.shoppingcart.repository.ProductRepository;
import com.example.shoppingcart.repository.ShoppingCartRepository;
import com.example.shoppingcart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

//    @Mock
    @Autowired
    private ProductRepository productRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private ShoppingCartController shoppingCartController;



    Product product1=new Product(1L,"XBOX",25L,3400L);


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @Test
    public void getAllProducts_success() throws Exception{
        List<Product>products=new ArrayList<>(Arrays.asList(product1));
        productRepository.save(product1);
        Mockito.when(productService.getAllProducts()).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/get-all-products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)));
    }



}
