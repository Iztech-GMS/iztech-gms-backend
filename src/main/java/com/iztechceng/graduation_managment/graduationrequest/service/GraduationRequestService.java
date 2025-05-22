package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.graduationrequest.model.dto.response.GraduationRequestResponse;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.ApprovalLog;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import com.iztechceng.graduation_managment.graduationrequest.repository.GraduationRequestRepository;
import com.iztechceng.graduation_managment.notification.service.NotificationService;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraduationRequestService {
    private final GraduationRequestRepository graduationRequestRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ApprovalLogService approvalLogService;
    private final NotificationService notificationService;

    public void createGraduationRequest(String studentEmail) {
        GraduationRequest graduationRequest = new GraduationRequest();

        Long studentId = getUserIdByEmail(studentEmail);
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

        notificationService.sendNotification(email,
                graduationRequest.getStudent().getEmail(),
                "Graduation request rejected");

    }

    public List<GraduationRequestResponse> getMyPendingRequests(String email) {
        Long userId = getUserIdByEmail(email);
        List<GraduationRequest> graduationRequests = graduationRequestRepository
                .findByApprover(userId);
        return graduationRequests.stream().map(GraduationRequestService::toResponse).toList();

    }

    public List<GraduationRequestResponse> getMyGraduationRequests(String email) {
        Long userId = getUserIdByEmail(email);
        List<GraduationRequest> graduationRequests = graduationRequestRepository
                .findByStudent(userId);
        return graduationRequests.stream().map(GraduationRequestService::toResponse).toList();

    }

    private Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found")).getId();
    }

    private boolean checkIfUserIsAuthorized(String email, Long graduationRequestId) {
        GraduationRequest graduationRequest = graduationRequestRepository.findById(graduationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Graduation request not found"));
        return graduationRequest.getApprover().getEmail().equals(email);

    }
    public static GraduationRequestResponse toResponse(GraduationRequest entity) {
        return GraduationRequestResponse.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .studentName(entity.getStudent().getName())
                .studentEmail(entity.getStudent().getEmail())
                .requestDate(entity.getRequestDate())
                .status(entity.getStatus())
                .approverName(entity.getApprover() != null ? entity.getApprover().getName() : null)
                .approverEmail(entity.getApprover() != null ? entity.getApprover().getEmail() : null)
                .approvalLogs(
                        entity.getApprovalLogs().stream()
                                .map(ApprovalLogService::toResponse)
                                .toList()
                )
                .build();
    }


}
