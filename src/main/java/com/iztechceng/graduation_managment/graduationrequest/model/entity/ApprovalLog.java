package com.iztechceng.graduation_managment.graduationrequest.model.entity;

import com.iztechceng.graduation_managment.graduationrequest.model.enums.ApproveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovalLog {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "graduation_request_id")
    private GraduationRequest graduationRequest;

    private String approverName;
    private String approverMail;
    private LocalDateTime approvalDate;
    @Enumerated(EnumType.STRING)
    private ApproveStatus approveStatus;
}
