package com.example.security_app.service;

import com.example.security_app.model.RoleEntity;
import com.example.security_app.model.UserEntity;
import com.example.security_app.repository.RoleRepo;
import com.example.security_app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity<String> addUser(UserEntity userEntity){
       try{
           Set<RoleEntity> roles = userEntity.getRoles().stream()
                   .map(role -> roleRepo.findByName(role.getName())
                           .orElseThrow(() -> new RuntimeException("Role " + role.getName() + " not found")))
                   .collect(Collectors.toSet());
           userEntity.setRoles(roles);
           String hashPwd = passwordEncoder.encode(userEntity.getPassword());
           userEntity.setPassword(hashPwd);
           UserEntity savedUser = userRepo.save(userEntity);
           if (savedUser.getId() > 0){
               return ResponseEntity.status(HttpStatus.CREATED)
               .body("User has created");
           }else {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Something wrong happened");
           }
       }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An exception occurred "+ ex.getMessage());
       }
    }

    public List<UserEntity> getAllUsers(){
        return this.userRepo.findAll();
    }

//    public UserEntity findUserByUserName(Authentication authentication){
//        return this.userRepo.findByEmail(authentication.getName())
//        .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
//    }
}
