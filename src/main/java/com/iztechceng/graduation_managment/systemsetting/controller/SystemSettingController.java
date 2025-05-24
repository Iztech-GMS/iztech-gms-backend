package com.iztechceng.graduation_managment.systemsetting.controller;

import com.iztechceng.graduation_managment.certification.CertificationDeterminerService;
import com.iztechceng.graduation_managment.systemsetting.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemSettingController {
    private final SystemSettingService systemSettingService;
    private final CertificationDeterminerService certificationDeterminerService;


    // close the graduation request interval
    @PreAuthorize("hasRole('STUDENTAFFAIRS')")
    @PutMapping("/admin/close-graduation-request")
    public ResponseEntity<?> closeGraduationRequestStatus() {
        systemSettingService.toggleGraduationRequest(false);
        certificationDeterminerService.assignCertificates();
        return ResponseEntity.ok("Graduation request interval closed.");
    }

    // open the graduation request interval
    @PreAuthorize("hasRole('STUDENTAFFAIRS')")
    @PutMapping("/admin/open-graduation-request")
    public ResponseEntity<?> openGraduationRequestStatus() {
        systemSettingService.toggleGraduationRequest(true);
        return ResponseEntity.ok("Graduation request interval opened.");
    }

    @PreAuthorize("hasAnyRole('ADVISOR','STUDENT', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @GetMapping("/api/v1/graduation-request-status")
    public ResponseEntity<?> getGraduationRequestStatus() {
        boolean isEnabled = systemSettingService.isGraduationRequestEnabled();
        if(isEnabled){
            return ResponseEntity.ok("Graduation request interval is open.");
        } else {
            return ResponseEntity.ok("Graduation request interval is closed.");
        }
    }


}
