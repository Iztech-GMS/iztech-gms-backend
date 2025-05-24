package com.iztechceng.graduation_managment.ranking.dto;

import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRankingResponse {
    private String name;
    private String email;
    private String department;
    private double gpa;
    private GraduationStatus graduationStatus;
}
