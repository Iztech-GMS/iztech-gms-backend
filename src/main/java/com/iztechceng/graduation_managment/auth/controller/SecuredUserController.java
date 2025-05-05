package com.iztechceng.graduation_managment.auth.controller;

import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserRequest;
import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserResponse;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import com.iztechceng.graduation_managment.auth.service.secured_user.SecuredUserService;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured-users")
@RequiredArgsConstructor
public class SecuredUserController {
    private final SecuredUserRepository securedUserRepository;
    private final SecuredUserService securedUserService;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADVISOR')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody SecuredUserRequest request) {
        if (securedUserRepository.findByEmail(request.getEmail()).isPresent()) {
            return  ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(securedUserService.create(request));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @GetMapping
    public ResponseEntity<List<SecuredUserResponse>> getAll() {
        return ResponseEntity.ok(securedUserService.getAll());
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<SecuredUserResponse> getById(@PathVariable Long id) {
        return securedUserService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<SecuredUserResponse> update(@PathVariable Long id, @RequestBody SecuredUserRequest request) {
        return ResponseEntity.ok(securedUserService.update(id, request));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        securedUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
