package com.iztechceng.graduation_managment.ranking.service;

import com.iztechceng.graduation_managment.ranking.dto.StudentRankingResponse;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import com.iztechceng.graduation_managment.ranking.repository.StudentRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentRankingService {
    private final StudentRankingRepository studentRankingRepository;

    public List<StudentRankingResponse> getGraduatedStudentsByGpa() {
        return studentRankingRepository.findByGraduationStatusOrderByGpaDesc(GraduationStatus.GRADUATED)
                .stream()
                .map(this::convertToStudentRankingResponse)
                .toList();
    }

    public List<StudentRankingResponse> getGraduatedStudentsByDepartmentAndGpa(String department) {
        return studentRankingRepository.findByGraduationStatusAndDepartmentOrderByGpaDesc(
                GraduationStatus.GRADUATED, 
                department
        )       .stream()
                .map(this::convertToStudentRankingResponse)
                .toList();
    }

    private StudentRankingResponse convertToStudentRankingResponse(Student student) {
        return StudentRankingResponse.builder()
                .name(student.getName())
                .email(student.getEmail())
                .department(student.getDepartment())
                .gpa(student.getGpa())
                .graduationStatus(student.getGraduationStatus())
                .certificates(student.getCertificates())
                .build();
    }

} 