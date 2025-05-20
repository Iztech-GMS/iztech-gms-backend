package com.iztechceng.graduation_managment.graduationrequest.model.entity;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.factory.GraduationRequestStateFactory;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;


public class GraduationRequestEntityListener {

    @PostLoad
    @PostPersist
    public void initState(GraduationRequest req) {
        // factory’yi statik helper’dan alacağız
        GraduationRequestStateFactory factory =
                SpringContext.getBean(GraduationRequestStateFactory.class);
        req.setState(factory.from(req.getStatus()));
    }

}

