package com.example.shoppingcart.service;

import com.example.shoppingcart.repository.AppUserRepository;
import com.example.shoppingcart.ShoppingCartApplication;
import com.example.shoppingcart.entity.appUser.AppUser;
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
public class AppUserServiceTest {
    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    public void findAppUserByEmailExists(){
        String existedEmail="malik.hilal14@gmail.com";
        AppUser AppUser=new AppUser("Malik",existedEmail);
        Mockito.when(appUserRepository.getAppUserByEmail(Mockito.anyString())).thenReturn(Optional.of(AppUser));
       Optional<AppUser>AppUser1= appUserService.getAppUserByEmail(existedEmail);
       assertEquals(true,AppUser1.isPresent());
    }
}
