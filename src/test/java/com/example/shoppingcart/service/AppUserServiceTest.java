package com.example.shoppingcart.service;

import com.example.shoppingcart.repository.AppUserRepository;
import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.appUser.AppUser;

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
public class AppUserServiceTest {
    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    public void findAppUserByEmailExists(){
        String existedEmail="malik.hilal14@gmail.com";
        AppUser appUser=new AppUser("Malik",existedEmail);
        Mockito.when(appUserRepository.getAppUserByEmail(Mockito.anyString())).thenReturn(Optional.of(appUser));
       Optional<AppUser>AppUser1= appUserService.getAppUserByEmail(existedEmail);
       assertEquals(true,AppUser1.isPresent());
    }
}
