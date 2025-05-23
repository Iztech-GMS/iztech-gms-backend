package com.iztechceng.graduation_managment.ranking.controller;

import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.ranking.service.StudentRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class StudentRankingController {
    private final StudentRankingService studentRankingService;

    @GetMapping("/graduated")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEAN', 'SECRETARY', 'STUDENTAFFAIRS')")
    public ResponseEntity<List<Student>> getGraduatedStudentsByGpa() {
        return ResponseEntity.ok(studentRankingService.getGraduatedStudentsByGpa());
    }

    @GetMapping("/graduated/department/{department}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEAN', 'SECRETARY', 'STUDENTAFFAIRS')")
    public ResponseEntity<List<Student>> getGraduatedStudentsByDepartmentAndGpa(
            @PathVariable String department) {
        return ResponseEntity.ok(studentRankingService.getGraduatedStudentsByDepartmentAndGpa(department));
    }
} 