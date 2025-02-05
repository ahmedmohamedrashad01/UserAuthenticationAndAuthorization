package com.example.security_app.filter;

import com.example.security_app.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
//Ahmed Rashad
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(null != authentication){
//            Environment env = getEnvironment();
//            if(null != env){
//                String secret = env.getProperty
//                (ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//                String jwt = Jwts.builder().issuer("Rashad")
//                    .subject("JWT Token")
//                    .claim("username", authentication.getName())
//                    .claim("authorities", authentication.getAuthorities()
//                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
//                    .issuedAt(new Date())
//                    .expiration(new Date((new Date()).getTime() + 500000000))
//                    .signWith(secretKey).compact();
//                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
//            }
//        }
//        filterChain.doFilter(request, response);
//---------------------------------------------------------------------------------------------
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null){
//            Environment env = getEnvironment();
//            if(env != null){
//                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//                String jwt = Jwts.builder().issuer("Ahmed Rashad").subject("JWT Token")
//                        .claim("username", authentication.getName())
//                        .claim("authorities", authentication.getAuthorities().stream()
//                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
//                        .issuedAt(new Date())
//                        .expiration(new Date((new Date()).getTime()+8000000))
//                        .signWith(secretKey).compact();
//                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
//            }
//        }
//        filterChain.doFilter(request, response);
//---------------------------------------------------------------------------------------------
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null){
//            Environment env = getEnvironment();
//            if(env != null){
//                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//                String jwt = Jwts.builder().issuer("Review Sec").subject("JWT Token")
//                        .claim("username", authentication.getName())
//                        .claim("authorities", authentication.getAuthorities().stream()
//                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
//                        .issuedAt(new Date())
//                        .expiration(new Date((new Date()).getTime()))
//                        .signWith(secretKey).compact();
//                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
//            }
//        }
//        filterChain.doFilter(request, response);
//---------------------------------------------------------------------------------------------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            Environment env = getEnvironment();
            if(env != null){
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer("Security app").subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 800000000))
                        .signWith(secretKey).compact();
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user/loginUser");
    }

}
