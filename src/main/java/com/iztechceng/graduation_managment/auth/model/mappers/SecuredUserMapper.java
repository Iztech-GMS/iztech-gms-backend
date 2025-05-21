package com.iztechceng.graduation_managment.auth.model.mappers;


import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserRequest;
import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserResponse;
import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;

public class SecuredUserMapper {

    public static SecuredUser toEntity(SecuredUserRequest dto) {
        return SecuredUser.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

    public static SecuredUserResponse toResponse(SecuredUser entity) {
        SecuredUserResponse response = new SecuredUserResponse();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        return response;
    }

}