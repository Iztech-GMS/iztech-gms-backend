package com.iztechceng.graduation_managment.graduationrequest.model.entity.states.stateımpl;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.ApprovalLog;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.GraduationRequestState;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import com.iztechceng.graduation_managment.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.iztechceng.graduation_managment.graduationrequest.service.ApprovalLogService;


@Component
@RequiredArgsConstructor
public class StudentAffairsPendingState implements GraduationRequestState {
    private final ApprovalLogService approvalLogService;
    private final NotificationService notificationService;

    @Override
    public GraduationRequest approve(GraduationRequest graduationRequest, String emailWhoApproved) {
        ApprovalLog approvalLog = approvalLogService.createApprovalLog(graduationRequest, emailWhoApproved,
                ApproveStatus.ACCEPTED);
        graduationRequest.addIntoApprovalLogs(approvalLog);
        graduationRequest.setStatus(RequestStatus.GRADUATED);
        //TODO: bunu gratuate edilmiş öğrenciler listesine ekleyeceğiz.!!!!
        graduationRequest.setApprover(null); // the next person who will save dont forget
        notificationService.sendNotification(emailWhoApproved,
                graduationRequest.getStudent().getEmail(),
                "Graduation request approved by student affairs" +
                        ". YOU ARE GRADUATED.");

        return graduationRequest;
    }

}
