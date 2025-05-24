package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusUpdateService {
    private final StudentRepository studentRepository;

    public void graduateStudent(Long studentId) {
        updateGraduationStatus(studentId, GraduationStatus.GRADUATED);
    }

    private void updateGraduationStatus(Long studentId, GraduationStatus status) {
        studentRepository.findById(studentId)
                .ifPresent(student -> {
                    student.setGraduationStatus(status);
                    studentRepository.save(student);
                });
    }

}
