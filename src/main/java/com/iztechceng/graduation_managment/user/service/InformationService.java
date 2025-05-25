package com.iztechceng.graduation_managment.user.service;

import com.iztechceng.graduation_managment.user.model.dto.StudentResponse;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationService {
    private final StudentRepository studentRepository;

    public StudentResponse getUserInformation(String email) {
        return studentRepository.findByEmail(email)
                .map(this::toDto)
                .orElse(null);
    }

    private StudentResponse toDto(Student student) {
        return StudentResponse.builder()
                .name(student.getName())
                .email(student.getEmail())
                .department(student.getDepartment())
                .gpa(student.getGpa())
                .graduationStatus(student.getGraduationStatus())
                .certificates(student.getCertificates())
                .build();
    }
}
