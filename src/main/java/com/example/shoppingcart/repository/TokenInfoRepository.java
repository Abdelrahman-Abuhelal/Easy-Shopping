package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.token.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenInfoRepository extends JpaRepository<TokenInfo,Long> {

    Optional<TokenInfo> findByRefreshToken(String refreshToken);
}
