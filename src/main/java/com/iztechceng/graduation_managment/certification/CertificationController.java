package com.iztechceng.graduation_managment.certification;

import com.iztechceng.graduation_managment.user.model.enums.CertificateType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping( "/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationDeterminerService certificationDeterminerService;

    @GetMapping()
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> checkStudentCertification(Principal principal) {
        String email = principal.getName();
        List<CertificateType> certificates = certificationDeterminerService.getStudentCertificate(email);
        return ResponseEntity.ok(certificates);

    }

}
