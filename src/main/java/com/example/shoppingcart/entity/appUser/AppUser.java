package com.example.shoppingcart.entity.appUser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    private Boolean locked = false;
    private Boolean enabled = true;
    public AppUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public AppUser(String username, String email,String password) {
        this.username = username;
        this.email = email;
        this.password=password;
    }
    public AppUser(String username, String email,String password,AppUserRole appUserRole) {
        this.username = username;
        this.email = email;
        this.password=password;
        this.role=appUserRole;
    }

    public AppUser(String customerUsername, String customerEmail, AppUserRole user) {
        username=customerUsername;
        email=customerEmail;
        role=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



}
