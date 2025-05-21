package com.iztechceng.graduation_managment.graduationrequest.model.entity.states.factory;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.GraduationRequestState;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.stateımpl.*;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraduationRequestStateFactory {
    private final AdvisorPendingState      advisorPending;
    private final SecretaryPendingState    secretaryPending;
    private final DeanPendingState         deanPending;
    private final StudentAffairsPendingState studentAffairsPending;
    private final GraduatedState         graduated;
    private final RejectedState         rejected;


    public GraduationRequestState from(RequestStatus status) {
        return switch (status) {
            case ADVISOR_PENDING  -> advisorPending;
            case STUDENT_AFFAIRS_PENDING -> studentAffairsPending;
            case SECRETARY_PENDING -> secretaryPending;
            case DEAN_PENDING -> deanPending;
            case GRADUATED ->graduated;
            case REJECTED -> rejected;

        };
    }
}
