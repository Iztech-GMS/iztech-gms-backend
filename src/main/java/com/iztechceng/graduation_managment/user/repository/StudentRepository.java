package com.iztechceng.graduation_managment.user.repository;

import com.iztechceng.graduation_managment.user.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

}
