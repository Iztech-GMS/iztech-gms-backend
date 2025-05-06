package com.iztechceng.graduation_managment.auth.controller;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;
import com.iztechceng.graduation_managment.auth.model.dto.request.LoginRequest;
import com.iztechceng.graduation_managment.auth.model.dto.response.JwtResponse;
import com.iztechceng.graduation_managment.auth.service.secured_user.SecuredUserService;
import com.iztechceng.graduation_managment.common.config.JwtService;
import com.iztechceng.graduation_managment.auth.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.QAbstractAuditable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecuredUserService securedUserService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword())
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);


        String name = userRegistrationService.getUserFullName(loginRequest.getEmail());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Convert roles to a list of strings
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Return the JWT, name, and roles
        return ResponseEntity.ok(new JwtResponse(jwt, name, roles));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @GetMapping("/advisor-only")
    public String advisorEndpoint() {
        return "Hello, advisor!";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-only")
    public String studentEndpoint() {
        return "Hello, Student!";
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if(securedUserService.confirmUserAccount(registerRequest.getEmail())) {
            if (!registerRequest.isPasswordMatching()) {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }else {
                try{
                    userRegistrationService.registerUser(registerRequest);
                    return ResponseEntity.ok("Registration successful.");
                }catch (Exception e){
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }
        }
        else {
            return ResponseEntity.badRequest().body("This email is not registered into the system" +
                    ". Please contact with Student Affairs.");
        }
    }



}
