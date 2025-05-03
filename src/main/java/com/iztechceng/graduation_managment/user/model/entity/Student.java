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

    private GraduationStatus graduationStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH})
    @JoinColumn(name = "advisor_id")
    private Advisor advisor;
}

