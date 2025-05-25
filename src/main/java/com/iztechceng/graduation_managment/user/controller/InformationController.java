package com.iztechceng.graduation_managment.user.controller;

import com.iztechceng.graduation_managment.user.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @GetMapping("/api/v1/information")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getInformation(Principal principal) {
        String email = principal.getName();
        var userInformation = informationService.getUserInformation(email);

        if (userInformation != null) {
            return ResponseEntity.ok(userInformation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
