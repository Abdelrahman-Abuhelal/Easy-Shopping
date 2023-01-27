package com.example.shoppingcart.controller;

import com.example.shoppingcart.entity.RegistrationRequest;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api-users")
public class AppUserRegistrationController {

    private final AppUserService appUserService;
    @Autowired
    public AppUserRegistrationController(AppUserService AppUserService) {
        this.appUserService = AppUserService;
    }


    @GetMapping("/get-all-users")
    public ResponseEntity<List<AppUser>> getAllAppUsers(){
        List<AppUser>appUsers=  appUserService.getAllAppUsers();
        return ResponseEntity.ok(appUsers);
    }
    @GetMapping("/get-all-customers")
    public ResponseEntity<List<AppUser>> getAllCustomers(){
        List<AppUser>appUsers=  appUserService.getAllCustomers();
        return ResponseEntity.ok(appUsers);
    }

    @GetMapping("/get-appUser/email/{email}")
    public ResponseEntity<AppUser>getAppUserByEmail(@PathVariable String email){
        Optional<AppUser> appUser=  appUserService.getAppUserByEmail(email);
        return ResponseEntity.ok(appUser.get());
    }

    @PostMapping("/register-customer")
    public ResponseEntity<?>registerCustomer(@RequestBody @Valid RegistrationRequest registrationRequest){
        AppUser appUser1= appUserService.registerCustomerUser(registrationRequest);
        return ResponseEntity.ok(appUser1);
    }



}
