package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.Customer;
import com.example.shoppingcart.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
class CustomerRepositoryTest {

//    @MockBean
    @Autowired
    private CustomerRepository customerRepository;



//    @ParameterizedTest
//    @ValueSource(strings = { "abd@gmail.com", "john@gmail.com" })

    @Test
    public void itShouldGetCustomerByHisEmailAndName() {
        //given
        String name="Abed";
        String email="abd.hilal14@gmail.com";
        Customer customer=new Customer(name,email);
        customerRepository.save(customer);
        //when
        Customer customer1= customerRepository.getCustomerByEmailAndName(email,name);
        assertEquals(customer1,customer);
    }





}