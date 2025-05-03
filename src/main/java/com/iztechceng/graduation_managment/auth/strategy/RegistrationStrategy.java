package com.iztechceng.graduation_managment.auth.strategy;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;

public interface RegistrationStrategy {
    boolean supports(String email);
    void register(RegisterRequest request, String fullName);
}
