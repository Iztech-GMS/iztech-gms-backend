package com.iztechceng.graduation_managment.ranking.service;

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

    public List<Student> getGraduatedStudentsByGpa() {
        return studentRankingRepository.findByGraduationStatusOrderByGpaDesc(GraduationStatus.GRADUATED);
    }

    public List<Student> getGraduatedStudentsByDepartmentAndGpa(String department) {
        return studentRankingRepository.findByGraduationStatusAndDepartmentOrderByGpaDesc(
                GraduationStatus.GRADUATED, 
                department
        );
    }
} 