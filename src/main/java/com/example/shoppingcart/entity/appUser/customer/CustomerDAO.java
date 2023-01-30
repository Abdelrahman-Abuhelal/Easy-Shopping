package com.example.shoppingcart.entity.appUser.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomerDAO {


    @NotBlank(message = "Username is mandatory, must not be black")
    @NotEmpty(message = "Username is mandatory, must not be empty")
//    @Column(unique = true)
    private String username;

    @NotBlank(message = "Email is mandatory, must not be black")
    @NotEmpty(message = "Email is mandatory, must not be empty")
//    @Column(unique = true)
    private String email;


    @NotBlank(message = "Password is mandatory, must not be black")
    @NotEmpty(message = "Password is mandatory, must not be empty")
    private String password;


}
