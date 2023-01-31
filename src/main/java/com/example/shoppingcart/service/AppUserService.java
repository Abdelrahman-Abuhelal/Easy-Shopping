package com.example.shoppingcart.service;

import com.example.shoppingcart.entity.appUser.customer.CustomerDTO;
import com.example.shoppingcart.entity.appUser.customer.CustomerDAO;
import com.example.shoppingcart.entity.appUser.customer.CustomerDTOMapper;
import com.example.shoppingcart.entity.appUser.AppUser;
import com.example.shoppingcart.entity.appUser.AppUserRole;
import com.example.shoppingcart.entity.token.TokenInfo;
import com.example.shoppingcart.exception.ResourceNotFoundException;
import com.example.shoppingcart.repository.AppUserRepository;
import com.example.shoppingcart.security.JWTResponseDto;
import com.example.shoppingcart.security.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private Logger log= LoggerFactory.getLogger(ProductService.class);
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final HttpServletRequest httpRequest;
    private final TokenInfoService tokenInfoService;


    @Autowired
    public AppUserService(AppUserRepository appUserRepository, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder, AuthenticationManager authManager, HttpServletRequest httpRequest, TokenInfoService tokenInfoService) {
        this.appUserRepository = appUserRepository;
        this.customerDTOMapper=customerDTOMapper;
        this.passwordEncoder=passwordEncoder;
        this.authManager = authManager;
        this.httpRequest = httpRequest;
        this.tokenInfoService = tokenInfoService;
    }

    public JWTResponseDto registerCustomer(CustomerDAO customerDAO) throws ConstraintViolationException{

          AppUser appUser= appUserRepository.save(new AppUser(customerDAO.getUsername(), customerDAO.getEmail(), customerDAO.getPassword(), AppUserRole.CUSTOMER));
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
            customerDAO.setPassword(passwordEncoder.encode(customerDAO.getPassword()));

            log.debug("Valid userDetails credentials.");

            AppUser userDetails = (AppUser) authentication.getPrincipal();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("SecurityContextHolder updated. [login={}]", appUser.getUsername());


            TokenInfo tokenInfo = createLoginToken(appUser.getUsername(), userDetails.getId());


            return JWTResponseDto.builder()
                    .accessToken(tokenInfo.getAccessToken())
                    .refreshToken(tokenInfo.getRefreshToken())
                    .build();


    }

    public TokenInfo createLoginToken(String userName, Long userId) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = JwtTokenUtils.generateToken(userName, accessTokenId, false);
        log.info("Access token created. [tokenId={}]", accessTokenId);

        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = JwtTokenUtils.generateToken(userName, refreshTokenId, true);
        log.info("Refresh token created. [tokenId={}]", accessTokenId);

        TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
        tokenInfo.setUser(new AppUser(userId));
        tokenInfo.setUserAgentText(userAgent);
        tokenInfo.setLocalIpAddress(ip.getHostAddress());
        tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
        // tokenInfo.setLoginInfo(createLoginInfoFromRequestUserAgent());
        return tokenInfoService.saveToken(tokenInfo);
    }



    public List<AppUser>getAllAppUsers(){
        return appUserRepository.findAll();
    }

    public List<CustomerDTO>getAllCustomers(){
        return appUserRepository.getAppUserByRole(AppUserRole.CUSTOMER).stream().map(customerDTOMapper).collect(Collectors.toList());
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
