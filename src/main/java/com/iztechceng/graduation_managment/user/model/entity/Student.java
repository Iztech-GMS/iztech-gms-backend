package com.iztechceng.graduation_managment.user.model.entity;


import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "studentId")
public class Student extends User {

    @Column(name = "graduation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GraduationStatus graduationStatus;

    @Column(name = "department", nullable = false)
    private String department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advisor_id")
    private Advisor advisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dean_id")
    private User dean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secretary_id")
    private User secretary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentaffairs_id")
    private User studentAffairs;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "total_earned_credits")
    private int totalEarnedCredits;

    @Column(name = "mandatory_completed")
    private boolean isMandatoryCourseCompleted;


}

