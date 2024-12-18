package com.example.security_app.controller;

import com.example.security_app.constants.ApplicationConstants;
import com.example.security_app.model.LoginRequestDTO;
import com.example.security_app.model.LoginResponseDTO;
import com.example.security_app.model.UserEntity;
import com.example.security_app.repository.UserRepo;
import com.example.security_app.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Environment env;



    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity){
        return this.userService.addUser(userEntity);
    }

//    @GetMapping("/loginUser")
//    public UserEntity findUserByUserName(String username){
//        return this.userService.findUserByUserName(username);
//    }

    @RequestMapping("/loginUser")
    public UserEntity getUserDetailsAfterLogin(Authentication authentication) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByEmail(authentication.getName());
        return optionalUserEntity.orElseThrow(null  );
    }

    @GetMapping("/test")
    public String getName(){
        return "Ahmed Rashad";
    }
    @GetMapping("/testUser")
    public String getDataForUser(){
        return "Welcome from user";
    }

    @GetMapping("/allUsers")
    public List<UserEntity> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin (@RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),
                loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("Ahmed Rashad").subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new java.util.Date())
                        .expiration(new java.util.Date((new java.util.Date()).getTime() + 400000))
                        .signWith(secretKey).compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
