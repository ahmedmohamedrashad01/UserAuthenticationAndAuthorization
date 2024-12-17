package com.example.security_app.config;

import com.example.security_app.exceptionhandling.CustomAccessDeniedHandler;
import com.example.security_app.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.example.security_app.filter.JWTTokenGeneratorFilter;
import com.example.security_app.filter.JWTTokenValidatorFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrfConfig -> csrfConfig.disable()
        .addFilterAfter(new JWTTokenGeneratorFilter(),
        BasicAuthenticationFilter.class)

        .addFilterBefore(new JWTTokenValidatorFilter(),
        BasicAuthenticationFilter.class))

       .authorizeHttpRequests((requests) -> requests
       .requestMatchers("/user/test").hasRole("ADMIN")
       .requestMatchers("/role/allRoles").hasRole("ADMIN")
       .requestMatchers("/user/allUsers").hasRole("ADMIN")
       .requestMatchers("/user/testUser").hasRole("USER")
       .requestMatchers("/role/add","/user/register","/user/loginUser","/user/apiLogin").permitAll()
              );

        http.formLogin(withDefaults()); /*UsernamePasswordAuthenticationFilter*/
//        http.httpBasic(withDefaults()); /*BaicAuthenticationFilter*/
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); /*BaicAuthenticationFilter*/
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        CustomAuthenticationProvider authenticationProvider =
                new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return  providerManager;
    }


}
