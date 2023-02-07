package com.example.shoppingcart;

import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.service.AppUserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {ShoppingCartApplication.class})
public class ProjectTest {
    @Autowired
    private AppUserService appUserService;

    @Test
    public void findAppUserByExistedEmail(){
        String existedEmail="malik.hilal14@gmail.com";
      Optional<AppUser> appUser= appUserService.getAppUserByEmail(existedEmail);
      assertEquals(true,appUser.isPresent());
      assertEquals(existedEmail,appUser.get().getEmail());
    }

    @Test
    public void findAppUserByNonExistedEmail(){
        String nonExistedEmail="NotExistedAppUser@gmail.com";
        Optional<AppUser> appUser= appUserService.getAppUserByEmail(nonExistedEmail);
        assertEquals(false,appUser.isPresent());
    }

}
