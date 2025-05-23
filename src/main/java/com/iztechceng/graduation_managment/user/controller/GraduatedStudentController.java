package com.iztechceng.graduation_managment.user.controller;

import com.iztechceng.graduation_managment.user.model.dto.response.GraduatedStudentResponse;
import com.iztechceng.graduation_managment.user.service.GraduatedStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/graduated-students")
@RequiredArgsConstructor
public class GraduatedStudentController {
    private final GraduatedStudentService graduatedStudentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DEAN', 'SECRETARY', 'STUDENTAFFAIRS')")
    public ResponseEntity<List<GraduatedStudentResponse>> getGraduatedStudentsByGpa() {
        try {
            return ResponseEntity.ok(graduatedStudentService.getGraduatedStudentsByGpa());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get graduated students by department, sorted by GPA (highest to lowest)
     * @param department Department name or "undecided" for students without a department
     * @return List of graduated students
     */
    @GetMapping("/department/{department}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEAN', 'SECRETARY', 'STUDENTAFFAIRS')")
    public ResponseEntity<List<GraduatedStudentResponse>> getGraduatedStudentsByDepartmentAndGpa(
            @PathVariable String department) {
        try {
            return ResponseEntity.ok(graduatedStudentService.getGraduatedStudentsByDepartmentAndGpa(department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 