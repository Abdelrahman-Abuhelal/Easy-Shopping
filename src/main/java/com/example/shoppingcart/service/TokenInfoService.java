package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.token.TokenInfo;
import com.example.shoppingcart.repository.TokenInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenInfoService {

    private final TokenInfoRepository tokenInfoRepository;

    public TokenInfo findById(Long id){
        return tokenInfoRepository.findById(id).orElse(null);
    }

    public Optional<TokenInfo> findByRefreshToken(String refreshToken){
        return tokenInfoRepository.findByRefreshToken(refreshToken);
    }

    public TokenInfo saveToken(TokenInfo tokenInfo){
        return tokenInfoRepository.save(tokenInfo);
    }


    public void deleteById(Long id) {
         tokenInfoRepository.deleteById(id);
         log.info(String.format("the token with id: %s is deleted"));
    }
}
