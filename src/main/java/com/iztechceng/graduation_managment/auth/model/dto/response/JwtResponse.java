package com.iztechceng.graduation_managment.auth.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String name;
    private List<String> roles;
}
