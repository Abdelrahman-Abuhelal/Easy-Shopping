package com.example.shoppingcart.service;

import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.order.Order;
import com.example.shoppingcart.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {ShoppingCartApplication.class})

public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testGetOrderDetail(){
        Order order=new Order();
        order.setId(1L);
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        Order testedOrder=orderService.getOrderDetail(1L);
        assertEquals(order,testedOrder);
    }
}
