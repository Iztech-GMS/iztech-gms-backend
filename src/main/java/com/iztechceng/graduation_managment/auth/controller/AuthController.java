package com.iztechceng.graduation_managment.auth.controller;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;
import com.iztechceng.graduation_managment.auth.service.secured_user.RegisterService;
import com.iztechceng.graduation_managment.auth.model.dto.request.LoginRequest;
import com.iztechceng.graduation_managment.auth.model.dto.response.JwtResponse;
import com.iztechceng.graduation_managment.common.config.JwtService;
import com.iztechceng.graduation_managment.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegisterService registerService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
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
        if(registerService.confirmUserAccount(registerRequest.getEmail())) {
            if (!registerRequest.isPasswordMatching()) {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }else {
                userService.registerUser(registerRequest);
                return ResponseEntity.ok("Registration successful.");
            }
        }
        else {
            return ResponseEntity.badRequest().body("This email is not registered into the system" +
                    ". Please contact with Student Affairs.");
        }
    }



}
