package com.example.shoppingcart.security;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}