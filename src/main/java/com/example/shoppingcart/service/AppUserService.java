package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.RegistrationRequest;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser registerCustomer(RegistrationRequest registrationRequest){
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
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
        if (!appUser1.isPresent()){
            throw new ResourceNotFoundException("The customer is not registered from this email and username");
        }
        return appUser1.get().getId();
    }

    public Optional<AppUser> getAppUserByEmail(String email){
        return appUserRepository.getAppUserByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser>appUser= appUserRepository.getAppUserByUsername(username);
        if (!appUser.isPresent()){
            throw new UsernameNotFoundException("the user is not found with selected username: "+username);
        }
        return new User(appUser.get().getUsername(),appUser.get().getPassword(),appUser.get().getAuthorities());
    }

//    private static List<GrantedAuthority> getAuthorities(AppUser appUser) {
//     }
}
