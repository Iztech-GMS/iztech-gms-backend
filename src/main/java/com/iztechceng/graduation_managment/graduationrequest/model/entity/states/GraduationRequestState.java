package com.iztechceng.graduation_managment.graduationrequest.model.entity.states;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;

public interface GraduationRequestState {
    GraduationRequest approve(GraduationRequest graduationRequest, String emailWhoApproved);
}
