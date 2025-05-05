package com.iztechceng.graduation_managment.auth.model.dto.request.secured_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SecuredUserResponse {
    private Long id;
    private String fullName;
    private String email;
}
