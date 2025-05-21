package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.ApprovalLog;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
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
    private final ApprovalLogService approvalLogService;

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
        if(!checkIfUserIsAuthorized(email, graduationRequestId)){
            throw new IllegalArgumentException("USER is not authorized to approve this request");
        }

        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));

        GraduationRequest graduationRequestToBeSaved = graduationRequest.approve(email);
        graduationRequestRepository.save(graduationRequestToBeSaved);

    }

    public void rejectGraduationRequest(String email, Long graduationRequestId) {
        //approve için gerekli insanda var mı onun kontrolü
        if(!checkIfUserIsAuthorized(email, graduationRequestId)){
            throw new IllegalArgumentException("USER is not authorized to reject this request");
        }
        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));

        ApprovalLog approvalLog = approvalLogService.createApprovalLog(graduationRequest, email,
                ApproveStatus.REJECTED);
        graduationRequest.addIntoApprovalLogs(approvalLog);
        graduationRequest.setStatus(RequestStatus.REJECTED);
        graduationRequest.setApprover(null);
        graduationRequestRepository.save(graduationRequest);

    }

    private Long getStudentIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found")).getId();
    }

    private boolean checkIfUserIsAuthorized(String email, Long graduationRequestId) {
        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));
        return graduationRequest.getApprover().getEmail().equals(email);

    }

}
