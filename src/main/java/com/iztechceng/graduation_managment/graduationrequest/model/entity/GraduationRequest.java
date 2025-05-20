package com.iztechceng.graduation_managment.graduationrequest.model.entity;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.states.GraduationRequestState;
import com.iztechceng.graduation_managment.graduationrequest.model.enums.RequestStatus;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(GraduationRequestEntityListener.class)  // ← register the listener
public class GraduationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status ;

    @Transient
    private GraduationRequestState state;

    @OneToMany(mappedBy = "graduationRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalLog> approvalLogs;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;


    // state‐delegation methods
    public GraduationRequest approve(String emailWhoApproved) {
        return state.approve(this, emailWhoApproved);
    }
    public GraduationRequest reject(String emailWhoRejected) {
        return state.reject(this, emailWhoRejected);
    }
    public void addIntoApprovalLogs(ApprovalLog approvalLog) {
        approvalLogs.add(approvalLog);
    }

}

