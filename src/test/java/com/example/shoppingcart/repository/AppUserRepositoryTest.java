package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.appUser.AppUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void findCustomerByNonExistedEmail(){
        String existedEmail="malik.hilal14@gmail.com";
        Optional<AppUser> customer= appUserRepository.getAppUserByEmail(existedEmail);
        assertEquals(true,customer.isPresent());
        assertEquals(existedEmail,customer.get().getEmail());
    }
}
