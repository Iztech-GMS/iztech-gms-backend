package com.iztechceng.graduation_managment.graduationrequest.model.dto.response;

import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalLogResponse {
    private Long id;
    private Long graduationRequestId;
    private String approverName;
    private String approverMail;
    private LocalDateTime approvalDate;
    private ApproveStatus approveStatus;
}
