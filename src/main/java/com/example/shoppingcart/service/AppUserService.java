package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.RegistrationRequest;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import com.example.shoppingcart.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser registerCustomerUser(RegistrationRequest registrationRequest){
        return appUserRepository.save(new AppUser(registrationRequest.getUsername(),registrationRequest.getEmail(),registrationRequest.getPassword(), AppUserRole.USER));
    }


    public List<AppUser>getAllAppUsers(){
        return appUserRepository.findAll();
    }

    public List<AppUser>getAllCustomers(){
        return appUserRepository.getAppUserByRole(AppUserRole.USER);
    }

    public Long isAppUserPresent(AppUser appUser){
        Optional<AppUser> appUser1= appUserRepository.getAppUserByEmailAndUsername(appUser.getEmail(), appUser.getUsername());
        return appUser1.isPresent() ? appUser1.get().getId():null;
    }

    public Optional<AppUser> getAppUserByEmail(String email){
        return appUserRepository.getAppUserByEmail(email);
    }


}
