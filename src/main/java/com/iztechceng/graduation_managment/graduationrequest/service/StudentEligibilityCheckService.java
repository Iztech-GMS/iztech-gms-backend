package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentEligibilityCheckService {
    private final StudentRepository studentRepository;

    public boolean checkIfStudentIsEligibleForGraduation(String email) {

        boolean creditFlag =  studentRepository.findByEmail(email)
                .map(student -> student.getTotalEarnedCredits() >= 240)
                .orElse(false);

        if(!creditFlag){
            throw new RuntimeException("Student is not eligible for graduation" +
                    " Total earned credits are less than 240.");
        }

        boolean gpaFlag = studentRepository.findByEmail(email)
                .map(student -> student.getGpa() >= 2)
                .orElse(false);

        if(!gpaFlag){
            throw new RuntimeException("Student is not eligible for graduation" +
                    " GPA is less than 2.");
        }

        boolean mandatoryCoursesFlag = studentRepository.findByEmail(email)
                .map(Student::isMandatoryCourseCompleted)
                .orElse(false);

        if(!mandatoryCoursesFlag){
            throw new RuntimeException("Student is not eligible for graduation" +
                    " Mandatory courses are not completed.");
        }

        return true;
    }

}
