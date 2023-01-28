package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser>  getAppUserByEmailAndUsername(String email, String username);
    Optional<AppUser>  getAppUserByEmail(String email);
    Optional<AppUser>  getAppUserByUsername(String username);
    List<AppUser> getAppUserByRole(AppUserRole role);

}
