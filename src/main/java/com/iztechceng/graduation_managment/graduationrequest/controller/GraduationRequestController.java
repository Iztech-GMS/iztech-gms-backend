package com.iztechceng.graduation_managment.graduationrequest.controller;

import com.iztechceng.graduation_managment.graduationrequest.model.dto.request.GraduationConsiderationRequest;
import com.iztechceng.graduation_managment.graduationrequest.service.GraduationRequestService;
import com.iztechceng.graduation_managment.graduationrequest.service.StudentEligibilityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/graduation-requests")
public class GraduationRequestController {
    private final GraduationRequestService graduationRequestService;
    private final StudentEligibilityCheckService studentEligibilityCheckService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping()
    public ResponseEntity<String> createGraduationRequest(Principal principal) {
        try{
            studentEligibilityCheckService.checkIfStudentIsEligibleForGraduation(principal.getName());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // TODO: BURADAN ONCE STUDENT BU REQUESTİ OLUŞTURABİLİR Mİ KONTROLÜ YAPICAKSINIZ.!!
        graduationRequestService.createGraduationRequest(principal.getName());
        return ResponseEntity.ok("Graduation request created successfully");


    }

    @PreAuthorize("hasAnyRole('ADVISOR', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @PostMapping("/approve")
    public ResponseEntity<String> approveGraduationRequest(Principal principal, @RequestBody GraduationConsiderationRequest graduationConsiderationRequest) {
        try {
            graduationRequestService.approveGraduationRequest(principal.getName(), graduationConsiderationRequest.getGraduationRequestId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error occurred while approving the graduation request: " + e.getMessage());
        }
        return ResponseEntity.ok("Graduation request approved successfully");
    }

    @PreAuthorize("hasAnyRole('ADVISOR', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @PostMapping("/reject")
    public ResponseEntity<String> rejectGraduationRequest(Principal principal, @RequestBody GraduationConsiderationRequest graduationConsiderationRequest) {
        try {
            graduationRequestService.rejectGraduationRequest(principal.getName(), graduationConsiderationRequest.getGraduationRequestId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error occurred while rejecting the graduation request: " + e.getMessage());
        }
        return ResponseEntity.ok("Graduation request rejected successfully");
    }

    @PreAuthorize("hasAnyRole('ADVISOR', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @GetMapping("/my-pending-requests")
    public ResponseEntity<?> getMyPendingRequests(Principal principal) {
        try {
            return ResponseEntity.ok(graduationRequestService.getMyPendingRequests(principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred while fetching pending requests: " + e.getMessage());
        }
    }



}














