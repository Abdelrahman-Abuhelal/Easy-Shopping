package com.example.shoppingcart.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {


    @NotBlank(message = "Username is mandatory, must not be black")
    @NotEmpty(message = "Username is mandatory, must not be empty")
    private String username;

    @NotBlank(message = "Email is mandatory, must not be black")
    @NotEmpty(message = "Email is mandatory, must not be empty")
    private String email;


    @NotBlank(message = "Password is mandatory, must not be black")
    @NotEmpty(message = "Password is mandatory, must not be empty")
    private String password;


}
