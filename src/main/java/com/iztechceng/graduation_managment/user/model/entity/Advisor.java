package com.iztechceng.graduation_managment.user.model.entity;

import com.iztechceng.graduation_managment.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Getter
@PrimaryKeyJoinColumn(name = "advisorId")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Advisor extends User {

    @OneToMany(mappedBy = "advisor")
    private List<Student> students;


}
