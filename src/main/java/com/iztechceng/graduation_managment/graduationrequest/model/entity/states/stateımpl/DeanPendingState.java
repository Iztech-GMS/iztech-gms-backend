package com.iztechceng.graduation_managment.graduationrequest.model.entity.states.stateımpl;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.GraduationRequestState;
import org.springframework.stereotype.Component;

@Component
public class DeanPendingState implements GraduationRequestState {
    @Override
    public GraduationRequest approve(GraduationRequest graduationRequest, String emailWhoApproved) {
        return null;
    }

    @Override
    public GraduationRequest reject(GraduationRequest graduationRequest, String emailWhoRejected) {
        return null;
    }
}
