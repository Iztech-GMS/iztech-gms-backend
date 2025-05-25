package com.iztechceng.graduation_managment.user.model.dto;

import com.iztechceng.graduation_managment.user.model.enums.CertificateType;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String name;
    private String email;
    private String department;
    private double gpa;
    private GraduationStatus graduationStatus;
    private List<CertificateType> certificates;
}
