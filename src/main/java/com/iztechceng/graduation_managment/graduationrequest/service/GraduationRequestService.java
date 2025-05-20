package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import com.iztechceng.graduation_managment.graduationrequest.repository.GraduationRequestRepository;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GraduationRequestService {
    private final GraduationRequestRepository graduationRequestRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public void createGraduationRequest(String studentEmail) {
        GraduationRequest graduationRequest = new GraduationRequest();

        Long studentId = getStudentIdByEmail(studentEmail);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        graduationRequest.setStudent(student);

        graduationRequest.setRequestDate(LocalDateTime.now());

        graduationRequest.setStatus(RequestStatus.ADVISOR_PENDING);

        graduationRequest.setApprover(student.getAdvisor());

        graduationRequestRepository.save(graduationRequest);

    }

    public void approveGraduationRequest(String email, Long graduationRequestId) {
        //approve için gerekli insanda var mı onun kontrolü
        if(!checkIfAdvisorIsAuthorized(email, graduationRequestId)){
            throw new IllegalArgumentException("USER is not authorized to approve this request");
        }

        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));

        GraduationRequest graduationRequestToBeSaved = graduationRequest.approve(email);
        graduationRequestRepository.save(graduationRequestToBeSaved);

    }

    private Long getStudentIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found")).getId();
    }

    private boolean checkIfAdvisorIsAuthorized(String email, Long graduationRequestId) {
        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));
        return graduationRequest.getApprover().getEmail().equals(email);

    }

}
