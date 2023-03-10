package com.example.shoppingcart.security;

import com.example.shoppingcart.entity.appUser.AppUserRole;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    String [] PUBLIC_END_POINTS = {"/api/v1/auth/login", "/api/v1/auth/refresh-token", "/api/v1/auth/logout","/api-users/register-customer"};
    @Autowired
    private JwtUnAuthResponse jwtUnAuthResponse;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .and().exceptionHandling().and()
                .httpBasic().authenticationEntryPoint(jwtUnAuthResponse).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests().requestMatchers(PUBLIC_END_POINTS).permitAll()
                .anyRequest().authenticated().and().addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);


//                .requestMatchers("/api-users/**").permitAll()
//                .requestMatchers("/api-product/**").permitAll()
//                .requestMatchers("/api-order/**").hasRole(AppUserRole.USER.toString())
//                .anyRequest().permitAll();

        return http.build();
    }


    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

}
