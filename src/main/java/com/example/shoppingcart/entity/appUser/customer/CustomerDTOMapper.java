package com.example.shoppingcart.entity.appUser.customer;

import com.example.shoppingcart.entity.appUser.AppUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class CustomerDTOMapper implements Function<AppUser, CustomerDTO> {
    @Override
    public CustomerDTO apply(AppUser appUser) {
        return new CustomerDTO(appUser.getUsername(), appUser.getEmail());
    }
}
