package com.iztechceng.graduation_managment.auth.model.dto.request.secured_user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SecuredUserRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;
}