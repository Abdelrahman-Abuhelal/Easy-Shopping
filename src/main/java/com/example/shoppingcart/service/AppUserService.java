package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.appUser.customer.CustomerDTO;
import com.example.shoppingcart.entity.appUser.customer.CustomerDAO;
import com.example.shoppingcart.entity.appUser.customer.CustomerDTOMapper;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.AppUserRepository;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private Logger log= LoggerFactory.getLogger(ProductService.class);
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository,CustomerDTOMapper customerDTOMapper,PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.customerDTOMapper=customerDTOMapper;
        this.passwordEncoder=passwordEncoder;
    }

    public CustomerDTO registerCustomer(CustomerDAO customerDAO) throws ConstraintViolationException{
        customerDAO.setPassword(passwordEncoder.encode(customerDAO.getPassword()));
        try
        {
          AppUser appUser= appUserRepository.save(new AppUser(customerDAO.getUsername(), customerDAO.getEmail(), customerDAO.getPassword(), AppUserRole.USER));
            return customerDTOMapper.apply(appUser);

        }
        catch (ConstraintViolationException e){
            log.error("the user has already registered");
            return null;
        }
    }


    public List<AppUser>getAllAppUsers(){
        return appUserRepository.findAll();
    }

    public List<CustomerDTO>getAllCustomers(){
        return appUserRepository.getAppUserByRole(AppUserRole.USER).stream().map(customerDTOMapper).collect(Collectors.toList());
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
            throw new UsernameNotFoundException(String.format("the user %s is not found with selected username: ",username));
        }
        return new User(appUser.get().getUsername(),appUser.get().getPassword(),appUser.get().getAuthorities());
    }

//    private static List<GrantedAuthority> getAuthorities(AppUser appUser) {
//     }
}
