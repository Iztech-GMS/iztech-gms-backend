package com.iztechceng.graduation_managment.graduationrequest.model.entity.states.stateımpl;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.ApprovalLog;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.GraduationRequestState;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import com.iztechceng.graduation_managment.graduationrequest.service.ApprovalLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecretaryPendingState implements GraduationRequestState {
    private final ApprovalLogService approvalLogService;

    @Override
    public GraduationRequest approve(GraduationRequest graduationRequest, String emailWhoApproved) {
        ApprovalLog approvalLog = approvalLogService.createApprovalLog(graduationRequest, emailWhoApproved,
                ApproveStatus.ACCEPTED);
        graduationRequest.addIntoApprovalLogs(approvalLog);
        graduationRequest.setStatus(RequestStatus.DEAN_PENDING);

        graduationRequest.setApprover(graduationRequest.getStudent().getDean()); // the next person who will save dont forget

        return graduationRequest;
    }

    @Override
    public GraduationRequest reject(GraduationRequest graduationRequest, String emailWhoRejected) {
        return null;
    }
}
