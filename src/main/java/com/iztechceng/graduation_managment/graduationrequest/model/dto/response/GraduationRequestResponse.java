package com.iztechceng.graduation_managment.graduationrequest.model.dto.response;

import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GraduationRequestResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private double gpa;
    private int totalEarnedCredits;
    private boolean isMandatoryCourseCompleted;
    private LocalDateTime requestDate;
    private RequestStatus status;
    private String approverName;
    private String approverEmail;
    private List<ApprovalLogResponse> approvalLogs;
}

