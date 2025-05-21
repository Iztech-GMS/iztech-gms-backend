package com.iztechceng.graduation_managment.graduationrequest.service;

import com.iztechceng.graduation_managment.auth.service.UserRegistrationService;
import com.iztechceng.graduation_managment.graduationrequest.model.dto.response.ApprovalLogResponse;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.ApprovalLog;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import com.iztechceng.graduation_managment.graduationrequest.repository.ApprovalLogRepository;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApprovalLogService {
    private final ApprovalLogRepository approvalLogRepository;
    private final UserRepository userRepository;

    public ApprovalLog createApprovalLog(GraduationRequest graduationRequest
            , String emailWhoApproved, ApproveStatus approveStatus) {

        User user = userRepository.findByEmail(emailWhoApproved)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setGraduationRequest(graduationRequest);
        approvalLog.setApproverName(user.getName());
        approvalLog.setApproverMail(emailWhoApproved);
        approvalLog.setApprovalDate(LocalDateTime.now());
        approvalLog.setApproveStatus(approveStatus);
        return approvalLogRepository.save(approvalLog);
    }

    public static ApprovalLogResponse toResponse(ApprovalLog approvalLog) {
        return ApprovalLogResponse.builder()
                .id(approvalLog.getId())
                .graduationRequestId(approvalLog.getGraduationRequest().getId())
                .approverName(approvalLog.getApproverName())
                .approverMail(approvalLog.getApproverMail())
                .approvalDate(approvalLog.getApprovalDate())
                .approveStatus(approvalLog.getApproveStatus())
                .build();
    }

}
